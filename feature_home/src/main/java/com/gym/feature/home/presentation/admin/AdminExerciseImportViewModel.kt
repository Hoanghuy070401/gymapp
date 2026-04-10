package com.gym.feature.home.presentation.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gym.feature.home.BuildConfig
import com.gym.feature.home.data.exercisedb.ExerciseFirebaseCache
import com.gym.feature.home.data.exercisedb.ExerciseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AdminImportState(
    val isLoading: Boolean = false,
    val bodyParts: List<String> = emptyList(),
    val importedParts: Set<String> = emptySet(),        // đã có trong Firebase
    val log: List<String> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class AdminExerciseImportViewModel @Inject constructor(
    private val repository: ExerciseRepository,
    private val cache: ExerciseFirebaseCache
) : ViewModel() {

    private val _state = MutableStateFlow(AdminImportState())
    val state: StateFlow<AdminImportState> = _state.asStateFlow()

    private val apiKey get() = BuildConfig.EXERCISEDB_API_KEY

    init { loadBodyParts() }

    private fun loadBodyParts() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            // Fetch từ API để có danh sách đầy đủ
            repository.getBodyPartList(apiKey).onSuccess { parts ->
                // Kiểm tra part nào đã import vào Firebase
                val imported = parts.filter { cache.hasBodyPart(it) }.toSet()
                _state.update { it.copy(
                    isLoading = false,
                    bodyParts = parts,
                    importedParts = imported
                )}
            }.onFailure { e ->
                _state.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    /** Import exercises cho 1 body part cụ thể — limit 100 records */
    fun importBodyPart(bodyPart: String, limit: Int = 100) {
        if (_state.value.isLoading) return
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            appendLog("⏳ Đang fetch '$bodyPart' từ API... (limit=$limit)")

            repository.fetchRawExercisesForImport(apiKey, bodyPart = bodyPart, limit = limit)
                .onSuccess { exercises ->
                    appendLog("📥 Fetch xong: ${exercises.size} bài tập")
                    cache.pushExercises(bodyPart, exercises)
                        .onSuccess { count ->
                            val imported = _state.value.importedParts + bodyPart
                            _state.update { it.copy(
                                isLoading = false,
                                importedParts = imported
                            )}
                            appendLog("✅ Đã lưu $count bài tập '$bodyPart' lên Firebase!")
                        }
                        .onFailure { e ->
                            _state.update { it.copy(isLoading = false, error = e.message) }
                            appendLog("❌ Lỗi lưu Firebase: ${e.message}")
                        }
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, error = e.message) }
                    appendLog("❌ Lỗi fetch API: ${e.message}")
                }
        }
    }

    /** Import TẤT CẢ body parts tuần tự */
    fun importAll(limit: Int = 100) {
        if (_state.value.isLoading) return
        viewModelScope.launch {
            val parts = _state.value.bodyParts
            if (parts.isEmpty()) {
                appendLog("⚠️ Chưa có danh sách body parts")
                return@launch
            }
            appendLog("🚀 Bắt đầu import TẤT CẢ ${parts.size} body parts...")

            // Push body part list lên Firebase
            cache.pushBodyPartList(parts)

            parts.forEachIndexed { i, part ->
                appendLog("[${ i + 1}/${parts.size}] Đang import '$part'...")
                repository.fetchRawExercisesForImport(apiKey, bodyPart = part, limit = limit)
                    .onSuccess { exercises ->
                        cache.pushExercises(part, exercises)
                            .onSuccess { count ->
                                val imported = _state.value.importedParts + part
                                _state.update { it.copy(importedParts = imported) }
                                appendLog("  ✅ '$part': $count bài tập")
                            }
                            .onFailure { appendLog("  ❌ '$part': Lỗi Firebase") }
                    }
                    .onFailure { appendLog("  ❌ '$part': Lỗi API") }
            }
            _state.update { it.copy(isLoading = false) }
            appendLog("🎉 Hoàn thành import tất cả!")
        }
    }

    private fun appendLog(message: String) {
        _state.update { it.copy(log = listOf(message) + it.log) }
    }
}
