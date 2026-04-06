package com.gym.feature.auth.presentation;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0007\u0018\u0000 .2\u00020\u0001:\u0001.B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001c\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00130\u0017J\u0006\u0010\u0018\u001a\u00020\u0013J>\u0010\u0019\u001a\u00020\u00132\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00130\u00172\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00130\u00172\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00130\u00172\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00130\u0017J\u0006\u0010\u001e\u001a\u00020\u0013J\u0016\u0010\u001f\u001a\u00020\u00132\u0006\u0010 \u001a\u00020\u00152\u0006\u0010!\u001a\u00020\u0015J&\u0010\"\u001a\u00020\u00132\u0006\u0010 \u001a\u00020\u00152\u0006\u0010!\u001a\u00020\u00152\u0006\u0010#\u001a\u00020\u00152\u0006\u0010$\u001a\u00020\u0015J\u0006\u0010%\u001a\u00020\u0013J\u001c\u0010&\u001a\u00020\u00132\u0006\u0010 \u001a\u00020\u00152\f\u0010\'\u001a\b\u0012\u0004\u0012\u00020\u00130\u0017J\u0016\u0010(\u001a\u00020\u00132\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020\u0015J\u0006\u0010,\u001a\u00020\u0013J\u0006\u0010-\u001a\u00020\u0013R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\b\u001a\u00020\t8F\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\nR\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00070\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006/"}, d2 = {"Lcom/gym/feature/auth/presentation/AuthViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/gym/feature/auth/data/AuthRepository;", "(Lcom/gym/feature/auth/data/AuthRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/gym/feature/auth/presentation/AuthUiState;", "isAlreadyLoggedIn", "", "()Z", "pollingJob", "Lkotlinx/coroutines/Job;", "resendJob", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "changeEmailAndResendVerification", "", "newEmail", "", "onSuccess", "Lkotlin/Function0;", "checkEmailVerification", "checkPersistedSession", "onNotLoggedIn", "onNeedsVerification", "onNeedsSetup", "onLoggedIn", "clearError", "login", "email", "password", "register", "confirmPassword", "fullName", "resendVerificationEmail", "sendPasswordReset", "onSent", "signInWithGoogle", "context", "Landroid/content/Context;", "webClientId", "signOut", "startEmailVerificationPolling", "Companion", "feature_auth_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class AuthViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.gym.feature.auth.data.AuthRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.gym.feature.auth.presentation.AuthUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.gym.feature.auth.presentation.AuthUiState> uiState = null;
    
    /**
     * Coroutine running exponential-backoff polling for email verification.
     */
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job pollingJob;
    
    /**
     * Coroutine running the resend-cooldown countdown.
     */
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job resendJob;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "AuthViewModel";
    @org.jetbrains.annotations.NotNull()
    public static final com.gym.feature.auth.presentation.AuthViewModel.Companion Companion = null;
    
    @javax.inject.Inject()
    public AuthViewModel(@org.jetbrains.annotations.NotNull()
    com.gym.feature.auth.data.AuthRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.gym.feature.auth.presentation.AuthUiState> getUiState() {
        return null;
    }
    
    public final boolean isAlreadyLoggedIn() {
        return false;
    }
    
    public final void login(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String password) {
    }
    
    public final void register(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String password, @org.jetbrains.annotations.NotNull()
    java.lang.String confirmPassword, @org.jetbrains.annotations.NotNull()
    java.lang.String fullName) {
    }
    
    /**
     * Start background polling with exponential back-off:
     *  3s → 5s → 8s → 13s → 15s → 15s → …
     * Cancels & replaces any previously running poll.
     * When verified, sets [AuthUiState.isEmailVerified] = true; the Screen
     * observes this via LaunchedEffect and triggers navigation.
     */
    public final void startEmailVerificationPolling() {
    }
    
    /**
     * Manual check triggered by "Xác nhận ngay" button.
     * Shows loading indicator while reloading; on success sets isEmailVerified
     * (Screen LaunchedEffect drives navigation); on failure shows an error.
     */
    public final void checkEmailVerification() {
    }
    
    /**
     * Resend the verification email and start a 60-second cooldown.
     * Silently ignored if cooldown is still active.
     */
    public final void resendVerificationEmail() {
    }
    
    /**
     * Send a verification link to [newEmail] via Firebase verifyBeforeUpdateEmail.
     * The address only changes in Firebase after the user clicks the link.
     * Updates [verificationEmail] in state and restarts auto-polling.
     */
    public final void changeEmailAndResendVerification(@org.jetbrains.annotations.NotNull()
    java.lang.String newEmail, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSuccess) {
    }
    
    public final void signInWithGoogle(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String webClientId) {
    }
    
    public final void sendPasswordReset(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSent) {
    }
    
    public final void clearError() {
    }
    
    public final void signOut() {
    }
    
    /**
     * Called from SplashScreen to restore a persisted Firebase session.
     * Firebase Auth caches the token locally — no network call needed to check.
     * If verified & setup done → [onLoggedIn]; if verified & no setup → [onNeedsSetup]
     * If not verified → [onNeedsVerification]; if not logged in → [onNotLoggedIn].
     */
    public final void checkPersistedSession(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNotLoggedIn, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNeedsVerification, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNeedsSetup, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onLoggedIn) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/gym/feature/auth/presentation/AuthViewModel$Companion;", "", "()V", "TAG", "", "feature_auth_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}