package com.gym.feature.home.presentation.video

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gym.feature.home.data.importer.BulkImporterService
import com.gym.feature.home.data.importer.ImportResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// ── State ─────────────────────────────────────────────────────────────────────

data class BulkImportState(
    val isRunning: Boolean = false,
    val progressCurrent: Int = 0,
    val progressTotal: Int = 0,
    val progressMessage: String = "",
    val result: ImportResult? = null,
    val error: String? = null
)

// ── ViewModel ─────────────────────────────────────────────────────────────────

@HiltViewModel
class BulkImportViewModel @Inject constructor(
    private val importer: BulkImporterService
) : ViewModel() {

    private val _state = MutableStateFlow(BulkImportState())
    val state: StateFlow<BulkImportState> = _state.asStateFlow()

    /**
     * Start bulk import. Safe to call multiple times (ignored if already running).
     * @param batchSize  number of WGER exercises to process (recommend 5–20)
     * @param offset     WGER pagination offset for subsequent runs
     */
    fun startImport(batchSize: Int = 10, offset: Int = 0) {
        if (_state.value.isRunning) return

        _state.update { it.copy(isRunning = true, result = null, error = null, progressCurrent = 0) }

        viewModelScope.launch {
            try {
                val result = importer.runImport(
                    batchSize = batchSize,
                    offset = offset,
                    onProgress = { current, total, message ->
                        _state.update {
                            it.copy(
                                progressCurrent = current,
                                progressTotal = total,
                                progressMessage = message
                            )
                        }
                    }
                )
                _state.update { it.copy(isRunning = false, result = result) }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isRunning = false,
                        error = e.message ?: "Import thất bại"
                    )
                }
            }
        }
    }

    fun clearResult() {
        _state.update { it.copy(result = null, error = null) }
    }
}
