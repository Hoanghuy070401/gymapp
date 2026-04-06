package com.gym.feature.auth.presentation

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseUser
import com.gym.core.base.GymLogger
import com.gym.feature.auth.data.AuthRepository
import com.gym.feature.auth.data.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val successUser: FirebaseUser? = null,
    val isEmailVerified: Boolean = false,
    val verificationEmail: String = "",
    val resendCooldown: Int = 0,
    /** true = email/password login but email not yet verified — route to EmailVerification */
    val requiresEmailVerification: Boolean = false,
    /** true = user has completed the setup wizard (read from DB after login) */
    val isSetupCompleted: Boolean = false
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    /** Coroutine running exponential-backoff polling for email verification. */
    private var pollingJob: Job? = null

    /** Coroutine running the resend-cooldown countdown. */
    private var resendJob: Job? = null

    /** Check if already authenticated — used to skip Login screen */
    val isAlreadyLoggedIn: Boolean get() = repository.isLoggedIn

    // ── Email / Password ─────────────────────────────────────────────────

    fun login(email: String, password: String) {
        GymLogger.d(TAG, "login: $email")
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            when (val result = repository.loginWithEmail(email, password)) {
                is AuthResult.Success -> {
                    val user = result.user
                    GymLogger.i(TAG, "login success uid=${user.uid} emailVerified=${user.isEmailVerified}")
                    if (!user.isEmailVerified) {
                        // Email/password account, not yet verified — send to verification screen
                        try { repository.sendEmailVerification() } catch (_: Exception) { }
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                successUser = user,
                                verificationEmail = email.trim(),
                                requiresEmailVerification = true,
                                isSetupCompleted = false
                            )
                        }
                    } else {
                        // Verified — check setup completion
                        val setupDone = repository.checkSetupCompleted(user.uid)
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                successUser = user,
                                requiresEmailVerification = false,
                                isSetupCompleted = setupDone
                            )
                        }
                    }
                }
                is AuthResult.Error -> {
                    GymLogger.e(TAG, "login error: ${result.message}")
                    _uiState.update { it.copy(isLoading = false, error = result.message) }
                }
                else -> {}
            }
        }
    }

    fun register(email: String, password: String, confirmPassword: String, fullName: String) {
        GymLogger.d(TAG, "register: $email")
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            when (val result = repository.registerWithEmail(email, password, confirmPassword, fullName)) {
                is AuthResult.Success -> {
                    GymLogger.i(TAG, "register success uid=${result.user.uid}")
                    // sendEmailVerification is now handled inside repository (best-effort)
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            successUser = result.user,
                            verificationEmail = email.trim(),
                            isEmailVerified = false
                        )
                    }
                }
                is AuthResult.Error -> {
                    GymLogger.e(TAG, "register error: ${result.message}")
                    _uiState.update { it.copy(isLoading = false, error = result.message) }
                }
                else -> {}
            }
        }
    }

    // ── Email Verification ───────────────────────────────────────────────

    /**
     * Start background polling with exponential back-off:
     *   3s → 5s → 8s → 13s → 15s → 15s → …
     * Cancels & replaces any previously running poll.
     * When verified, sets [AuthUiState.isEmailVerified] = true; the Screen
     * observes this via LaunchedEffect and triggers navigation.
     */
    fun startEmailVerificationPolling() {
        GymLogger.d(TAG, "startEmailVerificationPolling")
        pollingJob?.cancel()
        _uiState.update { it.copy(isEmailVerified = false) }
        pollingJob = viewModelScope.launch {
            val backoffMs = listOf(3_000L, 5_000L, 8_000L, 13_000L)
            var index = 0
            while (true) {
                val wait = if (index < backoffMs.size) backoffMs[index++] else 15_000L
                delay(wait)
                try {
                    if (repository.reloadAndCheckVerified()) {
                        GymLogger.i(TAG, "email verified via polling")
                        _uiState.update { it.copy(isEmailVerified = true) }
                        break
                    }
                } catch (e: Exception) {
                    GymLogger.w(TAG, "polling check failed — retrying: ${e.message}")
                    /* network hiccup — keep polling */
                }
            }
        }
    }

    /**
     * Manual check triggered by "Xác nhận ngay" button.
     * Shows loading indicator while reloading; on success sets isEmailVerified
     * (Screen LaunchedEffect drives navigation); on failure shows an error.
     */
    fun checkEmailVerification() {
        GymLogger.d(TAG, "checkEmailVerification (manual)")
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val verified = try {
                repository.reloadAndCheckVerified()
            } catch (e: Exception) {
                GymLogger.e(TAG, e, "checkEmailVerification failed")
                false
            }

            if (verified) {
                GymLogger.i(TAG, "checkEmailVerification: email verified")
                pollingJob?.cancel()
                _uiState.update { it.copy(isLoading = false, isEmailVerified = true) }
            } else {
                GymLogger.w(TAG, "checkEmailVerification: not yet verified")
                _uiState.update {
                    it.copy(isLoading = false, error = "Email chưa được xác minh. Kiểm tra hộp thư và thử lại.")
                }
            }
        }
    }

    /**
     * Resend the verification email and start a 60-second cooldown.
     * Silently ignored if cooldown is still active.
     */
    fun resendVerificationEmail() {
        if (_uiState.value.resendCooldown > 0) {
            GymLogger.d(TAG, "resendVerificationEmail: cooldown active, ignored")
            return
        }
        GymLogger.d(TAG, "resendVerificationEmail")
        resendJob?.cancel()
        resendJob = viewModelScope.launch {
            _uiState.update { it.copy(error = null) }
            try {
                repository.sendEmailVerification()
            } catch (e: Exception) {
                GymLogger.e(TAG, e, "resendVerificationEmail failed")
            }
            for (remaining in 60 downTo 0) {
                _uiState.update { it.copy(resendCooldown = remaining) }
                if (remaining > 0) delay(1_000)
            }
        }
    }

    /**
     * Send a verification link to [newEmail] via Firebase verifyBeforeUpdateEmail.
     * The address only changes in Firebase after the user clicks the link.
     * Updates [verificationEmail] in state and restarts auto-polling.
     */
    fun changeEmailAndResendVerification(newEmail: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                repository.changeEmailVerification(newEmail)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        verificationEmail = newEmail.trim(),
                        resendCooldown = 60
                    )
                }
                // Restart polling for the new address
                startEmailVerificationPolling()
                // Kick off resend cooldown
                resendJob?.cancel()
                resendJob = viewModelScope.launch {
                    for (remaining in 60 downTo 0) {
                        _uiState.update { it.copy(resendCooldown = remaining) }
                        if (remaining > 0) delay(1_000)
                    }
                }
                onSuccess()
            } catch (e: Exception) {
                GymLogger.e(TAG, e, "changeEmailAndResendVerification failed")
                _uiState.update {
                    it.copy(isLoading = false, error = "Không thể đổi email: ${e.localizedMessage}")
                }
            }
        }
    }

    fun signInWithGoogle(context: Context, webClientId: String) {
        GymLogger.d(TAG, "signInWithGoogle")
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val credentialManager = CredentialManager.create(context)
                val googleIdOption = GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(webClientId)
                    .setAutoSelectEnabled(true)
                    .build()

                val request = GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption)
                    .build()

                val credentialResponse = credentialManager.getCredential(context, request)
                val credential = credentialResponse.credential

                if (credential is CustomCredential &&
                    credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
                ) {
                    val googleIdToken = GoogleIdTokenCredential.createFrom(credential.data).idToken
                    when (val result = repository.loginWithGoogleCredential(googleIdToken)) {
                        is AuthResult.Success -> {
                            val user = result.user
                            GymLogger.i(TAG, "signInWithGoogle success uid=${user.uid}")
                            // Google users are always email-verified — just check setup
                            val setupDone = repository.checkSetupCompleted(user.uid)
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    successUser = user,
                                    requiresEmailVerification = false,
                                    isSetupCompleted = setupDone
                                )
                            }
                        }
                        is AuthResult.Error -> {
                            GymLogger.e(TAG, "signInWithGoogle auth error: ${result.message}")
                            _uiState.update { it.copy(isLoading = false, error = result.message) }
                        }
                        else -> {}
                    }
                } else {
                    GymLogger.e(TAG, "signInWithGoogle: unexpected credential type")
                    _uiState.update { it.copy(isLoading = false, error = "Google Sign-In thất bại.") }
                }
            } catch (e: GetCredentialCancellationException) {
                GymLogger.d(TAG, "signInWithGoogle: cancelled by user")
                _uiState.update { it.copy(isLoading = false, error = null) }
            } catch (e: Exception) {
                GymLogger.e(TAG, e, "signInWithGoogle unexpected error")
                _uiState.update {
                    it.copy(isLoading = false, error = "Google Sign-In thất bại: ${e.localizedMessage}")
                }
            }
        }
    }

    // ── Password Reset ────────────────────────────────────────────────────

    fun sendPasswordReset(email: String, onSent: () -> Unit) {
        GymLogger.d(TAG, "sendPasswordReset: $email")
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            when (val result = repository.sendPasswordReset(email)) {
                is AuthResult.PasswordResetSent -> {
                    GymLogger.i(TAG, "sendPasswordReset: email sent to $email")
                    _uiState.update { it.copy(isLoading = false) }
                    onSent()
                }
                is AuthResult.Error -> {
                    GymLogger.e(TAG, "sendPasswordReset error: ${result.message}")
                    _uiState.update { it.copy(isLoading = false, error = result.message) }
                }
                else -> {}
            }
        }
    }

    fun clearError() {
        GymLogger.d(TAG, "clearError")
        _uiState.update { it.copy(error = null) }
    }

    fun signOut() {
        GymLogger.i(TAG, "signOut")
        // Cancel background jobs first so they don't fire state updates after sign-out
        pollingJob?.cancel()
        pollingJob = null
        resendJob?.cancel()
        resendJob = null
        // Reset state to initial BEFORE signing out to prevent LaunchedEffects
        // in other screens from reacting to stale state (e.g. requiresEmailVerification=true
        // in LoginScreen triggering auto-redirect back to EmailVerification)
        _uiState.value = AuthUiState()
        repository.signOut()
    }

    /**
     * Called from SplashScreen to restore a persisted Firebase session.
     * Firebase Auth caches the token locally — no network call needed to check.
     * If verified & setup done → [onLoggedIn]; if verified & no setup → [onNeedsSetup]
     * If not verified → [onNeedsVerification]; if not logged in → [onNotLoggedIn].
     */
    fun checkPersistedSession(
        onNotLoggedIn: () -> Unit,
        onNeedsVerification: () -> Unit,
        onNeedsSetup: () -> Unit,
        onLoggedIn: () -> Unit
    ) {
        val user = repository.currentUser
        GymLogger.d(TAG, "checkPersistedSession user=${user?.uid}")
        if (user == null) {
            onNotLoggedIn()
            return
        }
        // Google users are always email-verified
        if (!user.isEmailVerified) {
            _uiState.update {
                it.copy(
                    successUser = user,
                    verificationEmail = user.email ?: "",
                    requiresEmailVerification = true
                )
            }
            onNeedsVerification()
            return
        }
        // Verified — check setup completion from DB
        viewModelScope.launch {
            val setupDone = repository.checkSetupCompleted(user.uid)
            _uiState.update {
                it.copy(successUser = user, isSetupCompleted = setupDone, requiresEmailVerification = false)
            }
            if (setupDone) onLoggedIn() else onNeedsSetup()
        }
    }

    companion object {
        private const val TAG = "AuthViewModel"
    }
}
