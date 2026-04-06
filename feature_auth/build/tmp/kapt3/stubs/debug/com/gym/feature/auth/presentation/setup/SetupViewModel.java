package com.gym.feature.auth.presentation.setup;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u0007\n\u0002\b\u0002\b\u0007\u0018\u0000 )2\u00020\u0001:\u0001)B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012J\u0014\u0010\u0013\u001a\u00020\u000f2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0015J\u000e\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u0018J\u000e\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u001bJ\u000e\u0010\u001c\u001a\u00020\u000f2\u0006\u0010\u001d\u001a\u00020\u0018J\u000e\u0010\u001e\u001a\u00020\u000f2\u0006\u0010\u001f\u001a\u00020\u0018J\u000e\u0010 \u001a\u00020\u000f2\u0006\u0010!\u001a\u00020\u001bJ\u001e\u0010\"\u001a\u00020\u000f2\u0006\u0010#\u001a\u00020\u00182\u0006\u0010$\u001a\u00020\u00182\u0006\u0010%\u001a\u00020\u0018J\u000e\u0010&\u001a\u00020\u000f2\u0006\u0010\'\u001a\u00020(R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006*"}, d2 = {"Lcom/gym/feature/auth/presentation/setup/SetupViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/gym/feature/auth/data/AuthRepository;", "imageStorage", "Lcom/gym/feature/auth/data/ProfileImageStorage;", "(Lcom/gym/feature/auth/data/AuthRepository;Lcom/gym/feature/auth/data/ProfileImageStorage;)V", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/gym/feature/auth/presentation/setup/SetupState;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "clearError", "", "saveProfileImage", "uri", "Landroid/net/Uri;", "syncToFirebaseAndComplete", "onSuccess", "Lkotlin/Function0;", "updateActivityLevel", "level", "", "updateAge", "age", "", "updateGender", "gender", "updateGoal", "goal", "updateHeight", "cm", "updateProfile", "nick", "mobile", "dob", "updateWeight", "kg", "", "Companion", "feature_auth_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class SetupViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.gym.feature.auth.data.AuthRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.gym.feature.auth.data.ProfileImageStorage imageStorage = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.gym.feature.auth.presentation.setup.SetupState> _state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.gym.feature.auth.presentation.setup.SetupState> state = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "SetupViewModel";
    @org.jetbrains.annotations.NotNull()
    public static final com.gym.feature.auth.presentation.setup.SetupViewModel.Companion Companion = null;
    
    @javax.inject.Inject()
    public SetupViewModel(@org.jetbrains.annotations.NotNull()
    com.gym.feature.auth.data.AuthRepository repository, @org.jetbrains.annotations.NotNull()
    com.gym.feature.auth.data.ProfileImageStorage imageStorage) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.gym.feature.auth.presentation.setup.SetupState> getState() {
        return null;
    }
    
    public final void updateGender(@org.jetbrains.annotations.NotNull()
    java.lang.String gender) {
    }
    
    public final void updateAge(int age) {
    }
    
    public final void updateWeight(float kg) {
    }
    
    public final void updateHeight(int cm) {
    }
    
    public final void updateGoal(@org.jetbrains.annotations.NotNull()
    java.lang.String goal) {
    }
    
    public final void updateActivityLevel(@org.jetbrains.annotations.NotNull()
    java.lang.String level) {
    }
    
    public final void updateProfile(@org.jetbrains.annotations.NotNull()
    java.lang.String nick, @org.jetbrains.annotations.NotNull()
    java.lang.String mobile, @org.jetbrains.annotations.NotNull()
    java.lang.String dob) {
    }
    
    public final void saveProfileImage(@org.jetbrains.annotations.NotNull()
    android.net.Uri uri) {
    }
    
    public final void clearError() {
    }
    
    /**
     * Sync all setup data to Firebase Realtime Database, then navigate to Home.
     * Called only once at final step (4.7 Fill Profile → "Start" button).
     */
    public final void syncToFirebaseAndComplete(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSuccess) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/gym/feature/auth/presentation/setup/SetupViewModel$Companion;", "", "()V", "TAG", "", "feature_auth_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}