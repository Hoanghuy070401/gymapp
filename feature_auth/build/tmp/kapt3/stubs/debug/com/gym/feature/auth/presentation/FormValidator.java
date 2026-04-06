package com.gym.feature.auth.presentation;

/**
 * Stateless validation utilities for auth forms.
 * All functions return null on valid, or an error string on invalid.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\r\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001a\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\t\u001a\u0004\u0018\u00010\bJ.\u0010\n\u001a\u00020\u00062\b\u0010\u000b\u001a\u0004\u0018\u00010\b2\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\t\u001a\u0004\u0018\u00010\b2\b\u0010\f\u001a\u0004\u0018\u00010\bJ\u0018\u0010\r\u001a\u0004\u0018\u00010\b2\u0006\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\bJ\u0010\u0010\u0010\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0011\u001a\u00020\bJ\u0010\u0010\u0012\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0013\u001a\u00020\bJ\u0010\u0010\u0014\u001a\u0004\u0018\u00010\b2\u0006\u0010\u000e\u001a\u00020\bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/gym/feature/auth/presentation/FormValidator;", "", "()V", "EMAIL_REGEX", "Lkotlin/text/Regex;", "isLoginValid", "", "emailError", "", "passwordError", "isRegisterValid", "nameError", "confirmError", "validateConfirmPassword", "password", "confirm", "validateEmail", "email", "validateFullName", "name", "validatePassword", "feature_auth_debug"})
public final class FormValidator {
    @org.jetbrains.annotations.NotNull()
    private static final kotlin.text.Regex EMAIL_REGEX = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.gym.feature.auth.presentation.FormValidator INSTANCE = null;
    
    private FormValidator() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String validateEmail(@org.jetbrains.annotations.NotNull()
    java.lang.String email) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String validatePassword(@org.jetbrains.annotations.NotNull()
    java.lang.String password) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String validateConfirmPassword(@org.jetbrains.annotations.NotNull()
    java.lang.String password, @org.jetbrains.annotations.NotNull()
    java.lang.String confirm) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String validateFullName(@org.jetbrains.annotations.NotNull()
    java.lang.String name) {
        return null;
    }
    
    /**
     * Returns true if all errors are null (form is valid)
     */
    public final boolean isLoginValid(@org.jetbrains.annotations.Nullable()
    java.lang.String emailError, @org.jetbrains.annotations.Nullable()
    java.lang.String passwordError) {
        return false;
    }
    
    public final boolean isRegisterValid(@org.jetbrains.annotations.Nullable()
    java.lang.String nameError, @org.jetbrains.annotations.Nullable()
    java.lang.String emailError, @org.jetbrains.annotations.Nullable()
    java.lang.String passwordError, @org.jetbrains.annotations.Nullable()
    java.lang.String confirmError) {
        return false;
    }
}