package com.gym.feature.home.presentation.video

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gym.core.base.GymLogger
import com.gym.feature.home.data.importer.BulkImporterService
import com.gym.feature.home.data.importer.BulkImporterService.ExercisePreview
import com.gym.feature.home.data.importer.ImportResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// ── State ─────────────────────────────────────────────────────────────────────

data class BulkImportUiState(
    // Config
    val batchSize: Int = 10,
    val offset: Int = 0,

    // Phase 1: Preview list
    val isFetchingList: Boolean = false,
    val exercises: List<ExercisePreview> = emptyList(),
    val selectedIds: Set<Int> = emptySet(),
    val manualUrls: Map<Int, String> = emptyMap(),   // exerciseId → manual YouTube URL
    val fetchError: String? = null,

    // Phase 2: Import in-progress
    val isImporting: Boolean = false,
    val importProgress: Int = 0,
    val importTotal: Int = 0,
    val importProgressMsg: String = "",

    // Phase 3: Result
    val result: ImportResult? = null,
    val importError: String? = null
) {
    val allSelected: Boolean get() = exercises.isNotEmpty() && selectedIds.size == exercises.size
    val noneSelected: Boolean get() = selectedIds.isEmpty()
    val selectedCount: Int get() = selectedIds.size
}

// ── ViewModel ─────────────────────────────────────────────────────────────────

@HiltViewModel
class BulkImportViewModel @Inject constructor(
    private val service: BulkImporterService
) : ViewModel() {

    private val _state = MutableStateFlow(BulkImportUiState())
    val state: StateFlow<BulkImportUiState> = _state.asStateFlow()

    // ── Config ────────────────────────────────────────────────────────────────

    fun setBatchSize(value: Int) {
        _state.update { it.copy(batchSize = value.coerceIn(1, 25)) }
    }

    fun setOffset(value: Int) {
        _state.update { it.copy(offset = value.coerceAtLeast(0)) }
    }

    // ── Step 1: Fetch exercise list ───────────────────────────────────────────

    fun fetchExerciseList() {
        val s = _state.value
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isFetchingList = true,
                    exercises = emptyList(),
                    selectedIds = emptySet(),
                    fetchError = null,
                    result = null,
                    importError = null
                )
            }
            try {
                val previews = service.fetchExercisePreviews(
                    batchSize = s.batchSize,
                    offset = s.offset
                )
                _state.update { it.copy(isFetchingList = false, exercises = previews) }
                GymLogger.i(TAG, "Fetched ${previews.size} exercises for preview")
            } catch (e: Exception) {
                GymLogger.e(TAG, e, "fetchExerciseList failed")
                _state.update {
                    it.copy(isFetchingList = false, fetchError = e.message ?: "Lỗi kết nối")
                }
            }
        }
    }

    // ── Step 2: Selection ─────────────────────────────────────────────────────

    fun toggleSelection(id: Int) {
        _state.update { s ->
            val newSet = if (id in s.selectedIds) s.selectedIds - id else s.selectedIds + id
            s.copy(selectedIds = newSet)
        }
    }

    fun selectAll() {
        _state.update { s ->
            s.copy(selectedIds = s.exercises.map { it.id }.toSet())
        }
    }

    fun clearSelection() {
        _state.update { it.copy(selectedIds = emptySet()) }
    }

    fun setManualUrl(exerciseId: Int, url: String) {
        _state.update { s ->
            val updated = s.manualUrls.toMutableMap()
            if (url.isBlank()) updated.remove(exerciseId) else updated[exerciseId] = url.trim()
            s.copy(manualUrls = updated)
        }
    }

    // ── Step 3: Import selected ───────────────────────────────────────────────

    fun importSelected() {
        val s = _state.value
        if (s.selectedIds.isEmpty()) return

        viewModelScope.launch {
            _state.update {
                it.copy(
                    isImporting = true,
                    importProgress = 0,
                    importTotal = it.selectedIds.size,
                    importProgressMsg = "Bắt đầu...",
                    result = null,
                    importError = null
                )
            }
            try {
                val result = service.runImportSelected(
                    selectedIds = s.selectedIds,
                    manualUrls = s.manualUrls,
                    onProgress = { current, total, msg ->
                        _state.update {
                            it.copy(
                                importProgress = current,
                                importTotal = total,
                                importProgressMsg = msg
                            )
                        }
                    }
                )
                _state.update {
                    it.copy(
                        isImporting = false,
                        result = result,
                        exercises = emptyList(),
                        selectedIds = emptySet(),
                        manualUrls = emptyMap()
                    )
                }
            } catch (e: Exception) {
                GymLogger.e(TAG, e, "importSelected failed")
                _state.update {
                    it.copy(isImporting = false, importError = e.message ?: "Import thất bại")
                }
            }
        }
    }

    fun dismissResult() {
        _state.update { it.copy(result = null, importError = null) }
    }

    companion object {
        private const val TAG = "BulkImportViewModel"
    }
}
