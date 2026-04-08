package com.gym.feature.home.presentation.video

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gym.core.base.GymLogger
import com.gym.feature.home.data.VideoRepository
import com.gym.feature.home.data.extractYouTubeVideoId
import com.gym.feature.home.data.importer.YoutubeVideoValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// ── UiEvents (one-shot via Channel — never re-fires on recomposition) ─────────

sealed class AddVideoUiEvent {
    data class Success(val message: String) : AddVideoUiEvent()
    data class Error(val message: String) : AddVideoUiEvent()
}

// ── Form State ─────────────────────────────────────────────────────────────────

data class AddVideoFormState(
    // Core fields
    val youtubeUrl: String = "",
    val title: String = "",
    val description: String = "",
    val durationText: String = "",
    val level: String = "Beginner",             // Beginner | Intermediate | Advanced

    // Tags (required per interview — strict validation, disallow submit if empty)
    val selectedAges: Set<String> = emptySet(), // "12-17", "18-25", "26-35", "36-50", "50+"
    val selectedGoals: Set<String> = emptySet(),// "Gain Weight", "Lose Weight", "Get Fitter", "Flexibility"
    val selectedBMIs: Set<String> = setOf("All"), // default All — optional override

    // Validation errors
    val urlError: String? = null,
    val titleError: String? = null,
    val agesError: String? = null,
    val goalsError: String? = null,

    // Submission state
    val isSubmitting: Boolean = false,
    val isValidating: Boolean = false,         // true while checking embeddable

    // Draft: tracks whether user has entered any data
    val hasDraft: Boolean = false
)

val AddVideoFormState.urlVideoId: String?
    get() = if (youtubeUrl.isNotBlank()) extractYouTubeVideoId(youtubeUrl) else null

val AddVideoFormState.isUrlValid: Boolean
    get() = urlVideoId != null

// ── ViewModel ─────────────────────────────────────────────────────────────────

@HiltViewModel
class AddVideoViewModel @Inject constructor(
    private val videoRepository: VideoRepository,
    private val youtubeValidator: YoutubeVideoValidator
) : ViewModel() {

    private val _form = MutableStateFlow(AddVideoFormState())
    val form: StateFlow<AddVideoFormState> = _form.asStateFlow()

    // One-shot channel — each event consumed exactly once
    private val _uiEvent = Channel<AddVideoUiEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    // ── Field updates ──────────────────────────────────────────────────────────

    fun onUrlChange(value: String) = _form.update {
        it.copy(youtubeUrl = value, urlError = null, hasDraft = true)
    }

    fun onTitleChange(value: String) = _form.update {
        it.copy(title = value, titleError = null, hasDraft = true)
    }

    fun onDescriptionChange(value: String) = _form.update {
        it.copy(description = value, hasDraft = true)
    }

    fun onDurationChange(value: String) {
        if (value.all(Char::isDigit)) _form.update { it.copy(durationText = value, hasDraft = true) }
    }

    fun onLevelChange(value: String) = _form.update { it.copy(level = value) }

    fun onToggleAge(age: String) = _form.update {
        val updated = it.selectedAges.toMutableSet().also { s ->
            if (s.contains(age)) s.remove(age) else s.add(age)
        }.toSet()
        it.copy(selectedAges = updated, agesError = null, hasDraft = true)
    }

    fun onToggleGoal(goal: String) = _form.update {
        val updated = it.selectedGoals.toMutableSet().also { s ->
            if (s.contains(goal)) s.remove(goal) else s.add(goal)
        }.toSet()
        it.copy(selectedGoals = updated, goalsError = null, hasDraft = true)
    }

    fun onToggleBMI(bmi: String) = _form.update {
        val updated = it.selectedBMIs.toMutableSet().also { s ->
            if (s.contains(bmi)) s.remove(bmi) else s.add(bmi)
        }.toSet()
        it.copy(selectedBMIs = updated)
    }

    // ── Validation ─────────────────────────────────────────────────────────────

    private fun validate(): Boolean {
        val f = _form.value
        var valid = true

        if (f.urlVideoId == null) {
            _form.update { it.copy(urlError = "URL YouTube không hợp lệ") }
            valid = false
        }
        if (f.title.isBlank()) {
            _form.update { it.copy(titleError = "Vui lòng nhập tiêu đề") }
            valid = false
        }
        if (f.selectedAges.isEmpty()) {
            _form.update { it.copy(agesError = "Vui lòng chọn ít nhất 1 nhóm tuổi") }
            valid = false
        }
        if (f.selectedGoals.isEmpty()) {
            _form.update { it.copy(goalsError = "Vui lòng chọn ít nhất 1 mục tiêu") }
            valid = false
        }
        return valid
    }

    // ── Submit ─────────────────────────────────────────────────────────────────

    fun submit() {
        if (!validate()) return
        val f = _form.value
        val videoId = f.urlVideoId ?: return

        viewModelScope.launch {
            // Step 1: validate embeddable
            _form.update { it.copy(isValidating = true, isSubmitting = false) }
            val validationError = youtubeValidator.validate(videoId)
            if (validationError != null) {
                GymLogger.w(TAG, "submit blocked: $validationError")
                _form.update { it.copy(isValidating = false, urlError = validationError) }
                _uiEvent.send(AddVideoUiEvent.Error("❌ $validationError"))
                return@launch
            }

            // Step 2: save to Firebase
            _form.update { it.copy(isValidating = false, isSubmitting = true) }
            try {
                videoRepository.addWorkoutVideo(
                    title = f.title.trim(),
                    youtubeUrl = f.youtubeUrl.trim(),
                    description = f.description.trim(),
                    durationMinutes = f.durationText.toIntOrNull() ?: 0,
                    level = f.level,
                    targetAges = f.selectedAges.toList(),
                    targetGoals = f.selectedGoals.toList(),
                    targetBMIs = f.selectedBMIs.toList()
                )
                GymLogger.i(TAG, "submit: success")
                _form.update { AddVideoFormState() }
                _uiEvent.send(AddVideoUiEvent.Success("✅ Video đã được thêm lên Firebase!"))
            } catch (e: Exception) {
                GymLogger.e(TAG, e, "submit failed")
                _form.update { it.copy(isSubmitting = false) }
                _uiEvent.send(AddVideoUiEvent.Error("❌ Thất bại: ${e.message}"))
            }
        }
    }

    companion object {
        private const val TAG = "AddVideoViewModel"
    }
}
