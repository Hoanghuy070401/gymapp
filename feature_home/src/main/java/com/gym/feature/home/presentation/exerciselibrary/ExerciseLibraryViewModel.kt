package com.gym.feature.home.presentation.exerciselibrary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gym.domain.model.ExerciseInfo
import com.gym.feature.home.BuildConfig
import com.gym.feature.home.data.exercisedb.ExerciseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ExerciseLibraryState(
    val isLoading: Boolean = false,
    val exercises: List<ExerciseInfo> = emptyList(),
    val bodyParts: List<String> = listOf("all"),
    val selectedBodyPart: String = "all",
    val searchQuery: String = "",
    val error: String? = null,
    val noApiKey: Boolean = false
)

@OptIn(FlowPreview::class)
@HiltViewModel
class ExerciseLibraryViewModel @Inject constructor(
    private val repository: ExerciseRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ExerciseLibraryState())
    val state: StateFlow<ExerciseLibraryState> = _state.asStateFlow()

    private val _searchQuery = MutableStateFlow("")

    private val apiKey: String get() = BuildConfig.EXERCISEDB_API_KEY

    init {
        if (apiKey.isBlank()) {
            _state.update { it.copy(noApiKey = true) }
        } else {
            loadBodyParts()
            loadExercises()
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
        loadExercises(bodyPart = bodyPart)
    }

    fun retry() {
        if (apiKey.isBlank()) return
        loadExercises(_state.value.selectedBodyPart)
    }

    private fun loadBodyParts() {
        viewModelScope.launch {
            repository.getBodyPartList(apiKey).onSuccess { parts ->
                _state.update { it.copy(bodyParts = listOf("all") + parts) }
            }
        }
    }

    private fun loadExercises(bodyPart: String = "all") {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            val result = if (bodyPart == "all") {
                repository.getExercises(apiKey, limit = 30)
            } else {
                repository.getExercisesByBodyPart(apiKey, bodyPart, limit = 30)
            }
            result
                .onSuccess { list ->
                    _state.update { it.copy(isLoading = false, exercises = list) }
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, error = "Không tải được dữ liệu: ${e.message}") }
                }
        }
    }

    private fun onSearch(query: String) {
        if (query.isBlank()) {
            loadExercises(_state.value.selectedBodyPart)
            return
        }
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            repository.searchByName(apiKey, query)
                .onSuccess { list ->
                    _state.update { it.copy(isLoading = false, exercises = list) }
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, error = "Lỗi tìm kiếm: ${e.message}") }
                }
        }
    }
}
