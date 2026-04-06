package com.gym.feature.auth.presentation.setup

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.gym.core.base.GymLogger
import com.gym.feature.auth.data.AuthRepository
import com.gym.feature.auth.data.ProfileImageStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SetupState(
    val gender: String = "Male",
    val age: Int = 20,
    val weightKg: Float = 60f,
    val heightCm: Int = 160,
    val goal: String = "",
    val activityLevel: String = "",
    // fullName is captured at registration — not re-entered in setup
    val nickname: String = "",
    val dateOfBirth: String = "",   // format DD/MM/YYYY
    val mobileNumber: String = "",
    val avatarPath: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class SetupViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val imageStorage: ProfileImageStorage
) : ViewModel() {

    private val _state = MutableStateFlow(SetupState(
        avatarPath = imageStorage.getSavedImagePath()
    ))
    val state: StateFlow<SetupState> = _state.asStateFlow()

    fun updateGender(gender: String) {
        GymLogger.d(TAG, "updateGender: $gender")
        _state.update { it.copy(gender = gender) }
    }

    fun updateAge(age: Int) {
        GymLogger.d(TAG, "updateAge: $age")
        _state.update { it.copy(age = age) }
    }

    fun updateWeight(kg: Float) {
        GymLogger.d(TAG, "updateWeight: $kg kg")
        _state.update { it.copy(weightKg = kg) }
    }

    fun updateHeight(cm: Int) {
        GymLogger.d(TAG, "updateHeight: $cm cm")
        _state.update { it.copy(heightCm = cm) }
    }

    fun updateGoal(goal: String) {
        GymLogger.d(TAG, "updateGoal: $goal")
        _state.update { it.copy(goal = goal) }
    }

    fun updateActivityLevel(level: String) {
        GymLogger.d(TAG, "updateActivityLevel: $level")
        _state.update { it.copy(activityLevel = level) }
    }

    fun updateProfile(nick: String, mobile: String, dob: String) {
        GymLogger.d(TAG, "updateProfile nick=$nick dob=$dob")
        _state.update {
            it.copy(nickname = nick, mobileNumber = mobile, dateOfBirth = dob)
        }
    }

    fun saveProfileImage(uri: Uri) {
        GymLogger.d(TAG, "saveProfileImage uri=$uri")
        val savedPath = imageStorage.saveProfileImage(uri)
        if (savedPath != null) {
            GymLogger.i(TAG, "saveProfileImage saved to $savedPath")
            _state.update { it.copy(avatarPath = savedPath) }
        } else {
            GymLogger.w(TAG, "saveProfileImage: imageStorage returned null path")
        }
    }

    fun clearError() {
        GymLogger.d(TAG, "clearError")
        _state.update { it.copy(error = null) }
    }

    /**
     * Sync all setup data to Firebase Realtime Database, then navigate to Home.
     * Called only once at final step (4.7 Fill Profile → "Start" button).
     */
    fun syncToFirebaseAndComplete(onSuccess: () -> Unit) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid == null) {
            GymLogger.e(TAG, "syncToFirebaseAndComplete: no current user — session expired")
            _state.update { it.copy(error = "Phiên đăng nhập hết hạn. Vui lòng đăng nhập lại.") }
            return
        }

        GymLogger.d(TAG, "syncToFirebaseAndComplete uid=$uid")
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val s = _state.value
                val profileMap: Map<String, Any> = buildMap {
                    if (s.gender.isNotBlank())       put("gender", s.gender)
                    put("age", s.age)
                    put("weightKg", s.weightKg.toDouble())
                    put("heightCm", s.heightCm)
                    if (s.goal.isNotBlank())          put("goal", s.goal)
                    if (s.activityLevel.isNotBlank()) put("activityLevel", s.activityLevel)
                    // fullName preserved from registration — not overwritten here
                    if (s.nickname.isNotBlank())      put("nickname", s.nickname)
                    if (s.dateOfBirth.isNotBlank())   put("dateOfBirth", s.dateOfBirth)
                    if (s.mobileNumber.isNotBlank())  put("mobileNumber", s.mobileNumber)
                    s.avatarPath?.let               { put("avatarPath", it) }
                }
                GymLogger.d(TAG, "syncToFirebaseAndComplete: saving ${profileMap.keys} for uid=$uid")

                // Save all profile fields
                repository.saveUserProfile(uid, profileMap)
                // Mark setup done — triggers smart routing on next launch
                repository.markSetupCompleted(uid)

                GymLogger.i(TAG, "syncToFirebaseAndComplete success uid=$uid")
                _state.update { it.copy(isLoading = false) }
                onSuccess()
            } catch (e: Exception) {
                GymLogger.e(TAG, e, "syncToFirebaseAndComplete failed uid=$uid")
                _state.update {
                    it.copy(isLoading = false, error = "Lưu dữ liệu thất bại. Vui lòng thử lại.")
                }
            }
        }
    }

    companion object {
        private const val TAG = "SetupViewModel"
    }
}
