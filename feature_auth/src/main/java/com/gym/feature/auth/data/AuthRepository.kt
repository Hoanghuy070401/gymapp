package com.gym.feature.auth.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import com.gym.core.base.GymLogger
import kotlinx.coroutines.tasks.await

/**
 * Single source of truth for Firebase Auth operations.
 * Returns sealed Result to let ViewModel handle UI state.
 */
class AuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance(
        "https://gym-app-d97c5-default-rtdb.asia-southeast1.firebasedatabase.app"
    )
) {

    val currentUser: FirebaseUser? get() = auth.currentUser
    val isLoggedIn: Boolean get() = auth.currentUser != null

    // ── Email / Password ─────────────────────────────────────────────────

    suspend fun loginWithEmail(email: String, password: String): AuthResult {
        GymLogger.d(TAG, "loginWithEmail: $email")
        if (email.isBlank() || password.isBlank()) {
            GymLogger.w(TAG, "loginWithEmail: blank credentials")
            return AuthResult.Error("Email và mật khẩu không được để trống.")
        }
        return try {
            val result = auth.signInWithEmailAndPassword(email.trim(), password).await()
            val user = result.user!!
            GymLogger.i(TAG, "loginWithEmail success uid=${user.uid}")
            AuthResult.Success(user)
        } catch (e: Exception) {
            GymLogger.e(TAG, e, "loginWithEmail failed for $email")
            AuthResult.Error(mapFirebaseError(e))
        }
    }

    suspend fun registerWithEmail(
        email: String,
        password: String,
        confirmPassword: String,
        fullName: String
    ): AuthResult {
        GymLogger.d(TAG, "registerWithEmail: $email")
        if (email.isBlank() || password.isBlank() || fullName.isBlank()) {
            GymLogger.w(TAG, "registerWithEmail: missing required fields")
            return AuthResult.Error("Vui lòng điền đầy đủ thông tin.")
        }
        if (password != confirmPassword) {
            GymLogger.w(TAG, "registerWithEmail: password mismatch")
            return AuthResult.Error("Mật khẩu xác nhận không khớp.")
        }
        if (password.length < 6) {
            GymLogger.w(TAG, "registerWithEmail: password too short")
            return AuthResult.Error("Mật khẩu phải có ít nhất 6 ký tự.")
        }
        return try {
            val result = auth.createUserWithEmailAndPassword(email.trim(), password).await()
            val user = result.user!!
            GymLogger.i(TAG, "registerWithEmail success uid=${user.uid}")
            // Save basic profile to Realtime Database
            saveUserToDatabase(user.uid, fullName, email.trim())
            // Send verification link — isolated so failure doesn't abort registration
            try {
                sendEmailVerification()
            } catch (e: Exception) {
                // Account created successfully; email delivery failed.
                // User can resend from the verification screen.
                GymLogger.e(TAG, e, "sendEmailVerification failed after register uid=${user.uid}")
            }
            AuthResult.Success(user)
        } catch (e: Exception) {
            GymLogger.e(TAG, e, "registerWithEmail failed for $email")
            AuthResult.Error(mapFirebaseError(e))
        }
    }

    // ── Google Sign-In ────────────────────────────────────────────────────

    suspend fun loginWithGoogleCredential(idToken: String): AuthResult {
        GymLogger.d(TAG, "loginWithGoogleCredential")
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val result = auth.signInWithCredential(credential).await()
            val user = result.user!!
            val isNew = result.additionalUserInfo?.isNewUser == true
            GymLogger.i(TAG, "loginWithGoogleCredential success uid=${user.uid} isNew=$isNew")
            if (isNew) {
                saveUserToDatabase(user.uid, user.displayName ?: "", user.email ?: "")
            }
            AuthResult.Success(user)
        } catch (e: Exception) {
            GymLogger.e(TAG, e, "loginWithGoogleCredential failed")
            AuthResult.Error(mapFirebaseError(e))
        }
    }

    // ── Password Reset ────────────────────────────────────────────────────

    suspend fun sendPasswordReset(email: String): AuthResult {
        GymLogger.d(TAG, "sendPasswordReset: $email")
        if (email.isBlank()) {
            GymLogger.w(TAG, "sendPasswordReset: blank email")
            return AuthResult.Error("Vui lòng nhập email.")
        }
        return try {
            auth.sendPasswordResetEmail(email.trim()).await()
            GymLogger.i(TAG, "sendPasswordReset: email sent to $email")
            AuthResult.PasswordResetSent
        } catch (e: Exception) {
            GymLogger.e(TAG, e, "sendPasswordReset failed for $email")
            AuthResult.Error(mapFirebaseError(e))
        }
    }

    // ── Database ──────────────────────────────────────────────────────────

    private suspend fun saveUserToDatabase(uid: String, name: String, email: String) {
        GymLogger.d(TAG, "saveUserToDatabase uid=$uid")
        val userRef = database.getReference("users/$uid")
        val userData = mapOf(
            "fullName" to name,
            "email" to email,
            "createdAt" to System.currentTimeMillis(),
            "isSetupCompleted" to false
        )
        try {
            userRef.setValue(userData).await()
            GymLogger.i(TAG, "saveUserToDatabase success uid=$uid")
        } catch (e: Exception) {
            GymLogger.e(TAG, e, "saveUserToDatabase failed uid=$uid")
            throw e // re-throw so registerWithEmail can report error to UI
        }
    }

    suspend fun markSetupCompleted(uid: String) {
        GymLogger.d(TAG, "markSetupCompleted uid=$uid")
        try {
            database.getReference("users/$uid/isSetupCompleted").setValue(true).await()
            GymLogger.i(TAG, "markSetupCompleted success uid=$uid")
        } catch (e: Exception) {
            GymLogger.e(TAG, e, "markSetupCompleted failed uid=$uid")
            throw e
        }
    }

    suspend fun saveUserProfile(uid: String, profileData: Map<String, Any>) {
        GymLogger.d(TAG, "saveUserProfile uid=$uid keys=${profileData.keys}")
        try {
            database.getReference("users/$uid").updateChildren(profileData).await()
            GymLogger.i(TAG, "saveUserProfile success uid=$uid")
        } catch (e: Exception) {
            GymLogger.e(TAG, e, "saveUserProfile failed uid=$uid")
            throw e
        }
    }

    fun signOut() {
        GymLogger.i(TAG, "signOut uid=${auth.currentUser?.uid}")
        auth.signOut()
    }

    /**
     * Read isSetupCompleted flag from Realtime DB.
     * Returns false on any error (safe default — user re-does setup).
     */
    suspend fun checkSetupCompleted(uid: String): Boolean {
        return try {
            val snap = database.getReference("users/$uid/isSetupCompleted").get().await()
            snap.getValue(Boolean::class.java) ?: false
        } catch (e: Exception) {
            GymLogger.w(TAG, "checkSetupCompleted failed uid=$uid: ${e.message}")
            false
        }
    }

    /**
     * Send a verification link to [newEmail]. Firebase will only apply the
     * address change AFTER the user clicks the link (verifyBeforeUpdateEmail).
     */
    suspend fun changeEmailVerification(newEmail: String) {
        val user = auth.currentUser ?: throw Exception("Phiên đăng nhập hết hạn, vui lòng đăng nhập lại.")
        GymLogger.d(TAG, "changeEmailVerification uid=${user.uid} newEmail=$newEmail")
        user.verifyBeforeUpdateEmail(newEmail.trim()).await()
        GymLogger.i(TAG, "changeEmailVerification: link sent to $newEmail")
    }

    // ── Email Verification ───────────────────────────────────────────────

    /**
     * Send a verification link to the current user's email.
     * Best-effort — caller may ignore failures.
     */
    suspend fun sendEmailVerification() {
        val user = auth.currentUser
        if (user == null) {
            GymLogger.w(TAG, "sendEmailVerification: currentUser is null — skipping")
            return
        }
        GymLogger.d(TAG, "sendEmailVerification uid=${user.uid} email=${user.email}")
        try {
            user.sendEmailVerification().await()
            GymLogger.i(TAG, "sendEmailVerification sent ok uid=${user.uid}")
        } catch (e: Exception) {
            GymLogger.e(TAG, e, "sendEmailVerification FAILED uid=${user.uid}")
            // re-throw so callers know the email wasn't sent
            throw e
        }
    }

    /**
     * Force-reload the user token from Firebase then return whether the email
     * is now verified. Reload is mandatory — the local cached flag does NOT
     * update without it.
     */
    suspend fun reloadAndCheckVerified(): Boolean {
        val uid = auth.currentUser?.uid
        GymLogger.d(TAG, "reloadAndCheckVerified uid=$uid")
        return try {
            auth.currentUser?.reload()?.await()
            val verified = auth.currentUser?.isEmailVerified ?: false
            GymLogger.d(TAG, "reloadAndCheckVerified result=$verified uid=$uid")
            verified
        } catch (e: Exception) {
            GymLogger.e(TAG, e, "reloadAndCheckVerified failed uid=$uid")
            false
        }
    }

    // ── Error mapping ─────────────────────────────────────────────────────

    /**
     * Extract the best identifier from a Firebase exception.
     * FirebaseAuthException.errorCode is the canonical source (e.g. "ERROR_EMAIL_ALREADY_IN_USE").
     * Falls back to e.message for non-FirebaseAuthException cases.
     */
    private fun mapFirebaseError(e: Exception): String {
        // Prefer errorCode from FirebaseAuthException (Android SDK canonical format)
        val code = (e as? FirebaseAuthException)?.errorCode ?: ""
        // Fall back to message for network/other exceptions
        val msg  = e.message ?: ""
        val key  = code.ifEmpty { msg }
        return mapFirebaseError(key)
    }

    private fun mapFirebaseError(key: String): String = when {
        key.isBlank() -> "Đã xảy ra lỗi. Vui lòng thử lại."

        // ── Wrong credentials ──────────────────────────────────────────
        "INVALID_LOGIN_CREDENTIALS"     in key ||
        "ERROR_INVALID_CREDENTIAL"      in key ||
        "invalid-credential"            in key ||
        "ERROR_WRONG_PASSWORD"          in key ||
        "wrong-password"                in key -> "Sai mật khẩu hoặc tài khoản."

        // ── User not found ─────────────────────────────────────────────
        "ERROR_USER_NOT_FOUND"          in key ||
        "USER_NOT_FOUND"                in key ||
        "user-not-found"                in key ||
        "no user record"                in key.lowercase() ->
            "Địa chỉ email này chưa được đăng ký, vui lòng đăng ký trước khi đăng nhập."

        // ── Email already in use ───────────────────────────────────────
        "ERROR_EMAIL_ALREADY_IN_USE"    in key ||
        "EMAIL_EXISTS"                  in key ||
        "email-already-in-use"          in key ||
        "already in use"                in key.lowercase() -> "Email này đã được đăng ký."

        // ── Invalid email ──────────────────────────────────────────────
        "ERROR_INVALID_EMAIL"           in key ||
        "INVALID_EMAIL"                 in key ||
        "invalid-email"                 in key -> "Email không hợp lệ."

        // ── Weak password ──────────────────────────────────────────────
        "ERROR_WEAK_PASSWORD"           in key ||
        "WEAK_PASSWORD"                 in key ||
        "weak-password"                 in key -> "Mật khẩu quá yếu. Dùng ít nhất 6 ký tự."

        // ── Network ────────────────────────────────────────────────────
        "network"               in key.lowercase() ||
        "unable to resolve host" in key.lowercase() ||
        "failed to connect"      in key.lowercase() ->
            "Không thể kết nối internet, vui lòng thử lại sau."

        // ── Rate limit ─────────────────────────────────────────────────
        "TOO_MANY_REQUESTS"             in key ||
        "ERROR_TOO_MANY_REQUESTS"       in key -> "Thử lại quá nhiều lần. Vui lòng chờ một lúc."

        else -> "Đã xảy ra lỗi. Vui lòng thử lại."
    }

    companion object {
        private const val TAG = "AuthRepository"
    }
}

sealed class AuthResult {
    data class Success(val user: FirebaseUser) : AuthResult()
    data class Error(val message: String) : AuthResult()
    data object PasswordResetSent : AuthResult()
}
