package com.gym.feature.auth.data;

/**
 * Single source of truth for Firebase Auth operations.
 * Returns sealed Result to let ViewModel handle UI state.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010$\n\u0002\b\t\u0018\u0000 22\u00020\u0001:\u00012B\u0019\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0086@\u00a2\u0006\u0002\u0010\u0012J\u0016\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\u0011H\u0086@\u00a2\u0006\u0002\u0010\u0012J\u001e\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00112\u0006\u0010\u0018\u001a\u00020\u0011H\u0086@\u00a2\u0006\u0002\u0010\u0019J\u0016\u0010\u001a\u001a\u00020\u00162\u0006\u0010\u001b\u001a\u00020\u0011H\u0086@\u00a2\u0006\u0002\u0010\u0012J\u0014\u0010\u001c\u001a\u00020\u00112\n\u0010\u001d\u001a\u00060\u001ej\u0002`\u001fH\u0002J\u0010\u0010\u001c\u001a\u00020\u00112\u0006\u0010 \u001a\u00020\u0011H\u0002J\u0016\u0010!\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u0011H\u0086@\u00a2\u0006\u0002\u0010\u0012J.\u0010\"\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00112\u0006\u0010\u0018\u001a\u00020\u00112\u0006\u0010#\u001a\u00020\u00112\u0006\u0010$\u001a\u00020\u0011H\u0086@\u00a2\u0006\u0002\u0010%J\u000e\u0010&\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\'J*\u0010(\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u00112\u0012\u0010)\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00010*H\u0086@\u00a2\u0006\u0002\u0010+J&\u0010,\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u00112\u0006\u0010-\u001a\u00020\u00112\u0006\u0010\u0017\u001a\u00020\u0011H\u0082@\u00a2\u0006\u0002\u0010.J\u000e\u0010/\u001a\u00020\u000fH\u0086@\u00a2\u0006\u0002\u0010\'J\u0016\u00100\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0011H\u0086@\u00a2\u0006\u0002\u0010\u0012J\u0006\u00101\u001a\u00020\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b8F\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u000b\u001a\u00020\f8F\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\r\u00a8\u00063"}, d2 = {"Lcom/gym/feature/auth/data/AuthRepository;", "", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "database", "Lcom/google/firebase/database/FirebaseDatabase;", "(Lcom/google/firebase/auth/FirebaseAuth;Lcom/google/firebase/database/FirebaseDatabase;)V", "currentUser", "Lcom/google/firebase/auth/FirebaseUser;", "getCurrentUser", "()Lcom/google/firebase/auth/FirebaseUser;", "isLoggedIn", "", "()Z", "changeEmailVerification", "", "newEmail", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "checkSetupCompleted", "uid", "loginWithEmail", "Lcom/gym/feature/auth/data/AuthResult;", "email", "password", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "loginWithGoogleCredential", "idToken", "mapFirebaseError", "e", "Ljava/lang/Exception;", "Lkotlin/Exception;", "key", "markSetupCompleted", "registerWithEmail", "confirmPassword", "fullName", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "reloadAndCheckVerified", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveUserProfile", "profileData", "", "(Ljava/lang/String;Ljava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveUserToDatabase", "name", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sendEmailVerification", "sendPasswordReset", "signOut", "Companion", "feature_auth_debug"})
public final class AuthRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.auth.FirebaseAuth auth = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.database.FirebaseDatabase database = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "AuthRepository";
    @org.jetbrains.annotations.NotNull()
    public static final com.gym.feature.auth.data.AuthRepository.Companion Companion = null;
    
    public AuthRepository(@org.jetbrains.annotations.NotNull()
    com.google.firebase.auth.FirebaseAuth auth, @org.jetbrains.annotations.NotNull()
    com.google.firebase.database.FirebaseDatabase database) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.google.firebase.auth.FirebaseUser getCurrentUser() {
        return null;
    }
    
    public final boolean isLoggedIn() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object loginWithEmail(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String password, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.gym.feature.auth.data.AuthResult> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object registerWithEmail(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String password, @org.jetbrains.annotations.NotNull()
    java.lang.String confirmPassword, @org.jetbrains.annotations.NotNull()
    java.lang.String fullName, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.gym.feature.auth.data.AuthResult> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object loginWithGoogleCredential(@org.jetbrains.annotations.NotNull()
    java.lang.String idToken, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.gym.feature.auth.data.AuthResult> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object sendPasswordReset(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.gym.feature.auth.data.AuthResult> $completion) {
        return null;
    }
    
    private final java.lang.Object saveUserToDatabase(java.lang.String uid, java.lang.String name, java.lang.String email, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object markSetupCompleted(@org.jetbrains.annotations.NotNull()
    java.lang.String uid, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object saveUserProfile(@org.jetbrains.annotations.NotNull()
    java.lang.String uid, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, ? extends java.lang.Object> profileData, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    public final void signOut() {
    }
    
    /**
     * Read isSetupCompleted flag from Realtime DB.
     * Returns false on any error (safe default — user re-does setup).
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object checkSetupCompleted(@org.jetbrains.annotations.NotNull()
    java.lang.String uid, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    /**
     * Send a verification link to [newEmail]. Firebase will only apply the
     * address change AFTER the user clicks the link (verifyBeforeUpdateEmail).
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object changeEmailVerification(@org.jetbrains.annotations.NotNull()
    java.lang.String newEmail, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Send a verification link to the current user's email.
     * Best-effort — caller may ignore failures.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object sendEmailVerification(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Force-reload the user token from Firebase then return whether the email
     * is now verified. Reload is mandatory — the local cached flag does NOT
     * update without it.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object reloadAndCheckVerified(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    /**
     * Extract the best identifier from a Firebase exception.
     * FirebaseAuthException.errorCode is the canonical source (e.g. "ERROR_EMAIL_ALREADY_IN_USE").
     * Falls back to e.message for non-FirebaseAuthException cases.
     */
    private final java.lang.String mapFirebaseError(java.lang.Exception e) {
        return null;
    }
    
    private final java.lang.String mapFirebaseError(java.lang.String key) {
        return null;
    }
    
    public AuthRepository() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/gym/feature/auth/data/AuthRepository$Companion;", "", "()V", "TAG", "", "feature_auth_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}