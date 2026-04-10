package com.gym.feature.home.presentation.admin;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u000bH\u0002J\u0006\u0010\u0015\u001a\u00020\u0013J\u0006\u0010\u0016\u001a\u00020\u0013J\u000e\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u000bJ\u0010\u0010\u0019\u001a\u00020\u00132\b\b\u0002\u0010\u001a\u001a\u00020\u001bJ\u0018\u0010\u001c\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u000b2\b\b\u0002\u0010\u001a\u001a\u00020\u001bJ\b\u0010\u001d\u001a\u00020\u0013H\u0002J\u0006\u0010\u001e\u001a\u00020\u0013R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\u00020\u000b8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\rR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u001f"}, d2 = {"Lcom/gym/feature/home/presentation/admin/AdminExerciseImportViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/gym/feature/home/data/exercisedb/ExerciseRepository;", "cache", "Lcom/gym/feature/home/data/exercisedb/ExerciseFirebaseCache;", "(Lcom/gym/feature/home/data/exercisedb/ExerciseRepository;Lcom/gym/feature/home/data/exercisedb/ExerciseFirebaseCache;)V", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/gym/feature/home/presentation/admin/AdminImportState;", "apiKey", "", "getApiKey", "()Ljava/lang/String;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "appendLog", "", "message", "cancelClearAll", "confirmClearAll", "deleteBodyPart", "bodyPart", "importAll", "limit", "", "importBodyPart", "loadBodyParts", "requestClearAll", "feature_home_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class AdminExerciseImportViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.gym.feature.home.data.exercisedb.ExerciseRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.gym.feature.home.data.exercisedb.ExerciseFirebaseCache cache = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.gym.feature.home.presentation.admin.AdminImportState> _state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.gym.feature.home.presentation.admin.AdminImportState> state = null;
    
    @javax.inject.Inject()
    public AdminExerciseImportViewModel(@org.jetbrains.annotations.NotNull()
    com.gym.feature.home.data.exercisedb.ExerciseRepository repository, @org.jetbrains.annotations.NotNull()
    com.gym.feature.home.data.exercisedb.ExerciseFirebaseCache cache) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.gym.feature.home.presentation.admin.AdminImportState> getState() {
        return null;
    }
    
    private final java.lang.String getApiKey() {
        return null;
    }
    
    private final void loadBodyParts() {
    }
    
    /**
     * Import exercises cho 1 body part cụ thể — limit 100 records
     */
    public final void importBodyPart(@org.jetbrains.annotations.NotNull()
    java.lang.String bodyPart, int limit) {
    }
    
    /**
     * Xóa toàn bộ bài tập của 1 body part khỏi Firebase
     */
    public final void deleteBodyPart(@org.jetbrains.annotations.NotNull()
    java.lang.String bodyPart) {
    }
    
    /**
     * Hiện/ẩn dialog xác nhận xóa tất cả
     */
    public final void requestClearAll() {
    }
    
    public final void cancelClearAll() {
    }
    
    /**
     * Xóa TOÀN BỘ exercises khỏi Firebase (sau xác nhận)
     */
    public final void confirmClearAll() {
    }
    
    /**
     * Import TẤT CẢ body parts tuần tự
     */
    public final void importAll(int limit) {
    }
    
    private final void appendLog(java.lang.String message) {
    }
}