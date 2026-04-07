package com.gym.feature.home.presentation;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0007\u0018\u0000 \u00142\u00020\u0001:\u0001\u0014B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0010\u001a\u00020\u0011H\u0002J\u0006\u0010\u0012\u001a\u00020\u0011J\u0006\u0010\u0013\u001a\u00020\u0011R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00070\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/gym/feature/home/presentation/HomeViewModel;", "Landroidx/lifecycle/ViewModel;", "videoRepository", "Lcom/gym/feature/home/data/VideoRepository;", "(Lcom/gym/feature/home/data/VideoRepository;)V", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/gym/feature/home/presentation/HomeState;", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "database", "Lcom/google/firebase/database/FirebaseDatabase;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "loadHomeData", "", "refresh", "retry", "Companion", "feature_home_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class HomeViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.gym.feature.home.data.VideoRepository videoRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.auth.FirebaseAuth auth = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.database.FirebaseDatabase database = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.gym.feature.home.presentation.HomeState> _state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.gym.feature.home.presentation.HomeState> state = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "HomeViewModel";
    
    /**
     * Firebase RTDB timeout — nếu không phản hồi sau 8s thì dùng seed data
     */
    private static final long FIREBASE_TIMEOUT_MS = 8000L;
    @org.jetbrains.annotations.NotNull()
    public static final com.gym.feature.home.presentation.HomeViewModel.Companion Companion = null;
    
    @javax.inject.Inject()
    public HomeViewModel(@org.jetbrains.annotations.NotNull()
    com.gym.feature.home.data.VideoRepository videoRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.gym.feature.home.presentation.HomeState> getState() {
        return null;
    }
    
    private final void loadHomeData() {
    }
    
    public final void retry() {
    }
    
    /**
     * Pull-to-refresh: reload dữ liệu từ Firebase, hiện spinner nhỏ ở trên cùng
     */
    public final void refresh() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0002J\u000e\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\bH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/gym/feature/home/presentation/HomeViewModel$Companion;", "", "()V", "FIREBASE_TIMEOUT_MS", "", "TAG", "", "defaultArticles", "", "Lcom/gym/feature/home/presentation/ArticleTip;", "defaultRoutines", "Lcom/gym/feature/home/presentation/RecommendedRoutine;", "feature_home_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        private final java.util.List<com.gym.feature.home.presentation.RecommendedRoutine> defaultRoutines() {
            return null;
        }
        
        private final java.util.List<com.gym.feature.home.presentation.ArticleTip> defaultArticles() {
            return null;
        }
    }
}