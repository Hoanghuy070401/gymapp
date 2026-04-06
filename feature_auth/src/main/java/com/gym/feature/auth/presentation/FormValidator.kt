package com.gym.feature.auth.presentation

/**
 * Stateless validation utilities for auth forms.
 * All functions return null on valid, or an error string on invalid.
 */
object FormValidator {

    private val EMAIL_REGEX = Regex("^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,}$")

    fun validateEmail(email: String): String? = when {
        email.isBlank() -> "Email không được để trống."
        !EMAIL_REGEX.matches(email.trim()) -> "Email không đúng định dạng."
        else -> null
    }

    fun validatePassword(password: String): String? = when {
        password.isBlank() -> "Mật khẩu không được để trống."
        password.length < 6 -> "Mật khẩu phải có ít nhất 6 ký tự."
        !password.any { it.isDigit() } -> "Mật khẩu phải chứa ít nhất 1 chữ số."
        else -> null
    }

    fun validateConfirmPassword(password: String, confirm: String): String? = when {
        confirm.isBlank() -> "Vui lòng xác nhận mật khẩu."
        confirm != password -> "Mật khẩu xác nhận không khớp."
        else -> null
    }

    fun validateFullName(name: String): String? = when {
        name.isBlank() -> "Họ tên không được để trống."
        name.trim().length < 2 -> "Họ tên phải có ít nhất 2 ký tự."
        else -> null
    }

    /** Returns true if all errors are null (form is valid) */
    fun isLoginValid(emailError: String?, passwordError: String?) =
        emailError == null && passwordError == null

    fun isRegisterValid(
        nameError: String?,
        emailError: String?,
        passwordError: String?,
        confirmError: String?
    ) = nameError == null && emailError == null && passwordError == null && confirmError == null
}
