package com.gym.feature.home.presentation.exerciselibrary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gym.core.translation.TranslatorManager
import com.gym.domain.model.ExerciseInfo
import com.gym.feature.home.BuildConfig
import com.gym.feature.home.data.UserProfile
import com.gym.feature.home.data.UserProfileRepository
import com.gym.feature.home.data.exercisedb.ExerciseAgeFilter
import com.gym.feature.home.data.exercisedb.ExerciseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ExerciseLibraryState(
    val isLoading: Boolean = false,
    val exercises: List<ExerciseInfo> = emptyList(),
    val bodyParts: List<Pair<String, String>> = listOf("all" to "all"),
    val selectedBodyPart: String = "all",
    val searchQuery: String = "",
    val error: String? = null,
    val noApiKey: Boolean = false,
    val isTranslating: Boolean = false,
    // Personalization
    val userProfile: UserProfile? = null,
    val isPersonalized: Boolean = false,  // true = đang lọc theo profile
    val ageGroupLabel: String = ""        // hiển thị "Thanh niên (25 tuổi)"
)

@OptIn(FlowPreview::class)
@HiltViewModel
class ExerciseLibraryViewModel @Inject constructor(
    private val repository: ExerciseRepository,
    private val translatorManager: TranslatorManager,
    private val profileRepository: UserProfileRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ExerciseLibraryState())
    val state: StateFlow<ExerciseLibraryState> = _state.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    private var userProfile: UserProfile? = null

    private val apiKey: String get() = BuildConfig.EXERCISEDB_API_KEY

    init {
        if (apiKey.isBlank()) {
            _state.update { it.copy(noApiKey = true) }
        } else {
            viewModelScope.launch {
                _state.update { it.copy(isTranslating = true) }
                // 1. Tải model dịch thuật
                translatorManager.downloadModelIfNeeded()
                // 2. Tải user profile để personalize
                loadUserProfile()
                // 3. Tải body parts + bài tập
                loadBodyParts()
                loadExercises()
            }
        }

        // Debounced search (500ms)
        _searchQuery
            .debounce(500)
            .distinctUntilChanged()
            .onEach { query -> onSearch(query) }
            .launchIn(viewModelScope)
    }

    fun onSearchQueryChanged(query: String) {
        _state.update { it.copy(searchQuery = query) }
        _searchQuery.value = query
    }

    fun onBodyPartSelected(bodyPart: String) {
        if (_state.value.selectedBodyPart == bodyPart) return
        _state.update { it.copy(selectedBodyPart = bodyPart, searchQuery = "", exercises = emptyList()) }
        _searchQuery.value = ""
        viewModelScope.launch { loadExercises(bodyPart = bodyPart) }
    }

    /** Bật/tắt cá nhân hoá theo độ tuổi & mục tiêu */
    fun togglePersonalization() {
        val current = _state.value.isPersonalized
        _state.update { it.copy(isPersonalized = !current) }
        viewModelScope.launch { loadExercises(_state.value.selectedBodyPart) }
    }

    fun retry() {
        if (apiKey.isBlank()) return
        viewModelScope.launch { loadExercises(_state.value.selectedBodyPart) }
    }

    // ── Private ───────────────────────────────────────────────────────────────

    private suspend fun loadUserProfile() {
        profileRepository.getCurrentUserProfile()
            .onSuccess { profile ->
                userProfile = profile
                _state.update {
                    it.copy(
                        userProfile = profile,
                        ageGroupLabel = ExerciseAgeFilter.ageGroupLabel(profile.age)
                    )
                }
            }
            // Profile không tải được → vẫn tiếp tục, chỉ không có personalization
    }

    private suspend fun loadBodyParts() {
        repository.getBodyPartList(apiKey).onSuccess { parts ->
            val trParts = parts.map {
                viewModelScope.async { it to translatorManager.translate(it) }
            }.awaitAll()
            val allTranslated = translatorManager.translate("all")
            _state.update { it.copy(bodyParts = listOf("all" to allTranslated) + trParts) }
        }
    }

    private suspend fun loadExercises(bodyPart: String = "all") {
        _state.update { it.copy(isLoading = true, error = null, isTranslating = true) }

        val result = if (bodyPart == "all") {
            repository.getExercises(apiKey, limit = 30)
        } else {
            repository.getExercisesByBodyPart(apiKey, bodyPart, limit = 30)
        }

        result
            .onSuccess { list ->
                // Apply age filter nếu bật personalization và có profile
                val filtered = applyPersonalization(list)
                val translated = translateExercises(filtered)
                _state.update { it.copy(isLoading = false, isTranslating = false, exercises = translated) }
            }
            .onFailure { e ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        isTranslating = false,
                        error = "Không tải được dữ liệu: ${e.message}"
                    )
                }
            }
    }

    private fun onSearch(query: String) {
        if (query.isBlank()) {
            viewModelScope.launch { loadExercises(_state.value.selectedBodyPart) }
            return
        }
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            repository.searchByName(apiKey, query)
                .onSuccess { list ->
                    val filtered = applyPersonalization(list)
                    val translated = translateExercises(filtered)
                    _state.update { it.copy(isLoading = false, exercises = translated) }
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, error = "Lỗi tìm kiếm: ${e.message}") }
                }
        }
    }

    /**
     * Áp dụng lọc & sắp xếp theo tuổi / mục tiêu / activity level.
     * Chỉ áp dụng khi isPersonalized = true và profile đã tải.
     */
    private fun applyPersonalization(list: List<ExerciseInfo>): List<ExerciseInfo> {
        val profile = userProfile ?: return list
        if (!_state.value.isPersonalized) return list
        return ExerciseAgeFilter.filterAndSort(
            exercises     = list,
            age           = profile.age,
            goal          = profile.goal,
            activityLevel = profile.activityLevel
        )
    }

    private suspend fun translateExercises(list: List<ExerciseInfo>): List<ExerciseInfo> {
        if (!translatorManager.isModelDownloaded) return list
        val jobs = list.map { ex ->
            viewModelScope.async {
                ex.copy(
                    // name giữ nguyên tiếng Anh
                    bodyPart         = translatorManager.translate(ex.bodyPart),
                    equipment        = translatorManager.translate(ex.equipment),
                    target           = translatorManager.translate(ex.target),
                    secondaryMuscles = translatorManager.translateList(ex.secondaryMuscles),
                    instructions     = translatorManager.translateList(ex.instructions)
                )
            }
        }
        return jobs.awaitAll()
    }
}
