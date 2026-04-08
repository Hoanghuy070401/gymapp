package com.gym.feature.home.presentation.video;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0007\u0018\u0000 \u001c2\u00020\u0001:\u0001\u001cB\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\f\u001a\u00020\rJ\u0006\u0010\u000e\u001a\u00020\rJ\u0006\u0010\u000f\u001a\u00020\rJ\u0006\u0010\u0010\u001a\u00020\rJ\u0006\u0010\u0011\u001a\u00020\rJ\u000e\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u0014J\u0016\u0010\u0015\u001a\u00020\r2\u0006\u0010\u0016\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\u0018J\u000e\u0010\u0019\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u0014J\u000e\u0010\u001a\u001a\u00020\r2\u0006\u0010\u001b\u001a\u00020\u0014R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u001d"}, d2 = {"Lcom/gym/feature/home/presentation/video/BulkImportViewModel;", "Landroidx/lifecycle/ViewModel;", "service", "Lcom/gym/feature/home/data/importer/BulkImporterService;", "(Lcom/gym/feature/home/data/importer/BulkImporterService;)V", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/gym/feature/home/presentation/video/BulkImportUiState;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "clearSelection", "", "dismissResult", "fetchExerciseList", "importSelected", "selectAll", "setBatchSize", "value", "", "setManualUrl", "exerciseId", "url", "", "setOffset", "toggleSelection", "id", "Companion", "feature_home_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class BulkImportViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.gym.feature.home.data.importer.BulkImporterService service = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.gym.feature.home.presentation.video.BulkImportUiState> _state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.gym.feature.home.presentation.video.BulkImportUiState> state = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "BulkImportViewModel";
    @org.jetbrains.annotations.NotNull()
    public static final com.gym.feature.home.presentation.video.BulkImportViewModel.Companion Companion = null;
    
    @javax.inject.Inject()
    public BulkImportViewModel(@org.jetbrains.annotations.NotNull()
    com.gym.feature.home.data.importer.BulkImporterService service) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.gym.feature.home.presentation.video.BulkImportUiState> getState() {
        return null;
    }
    
    public final void setBatchSize(int value) {
    }
    
    public final void setOffset(int value) {
    }
    
    public final void fetchExerciseList() {
    }
    
    public final void toggleSelection(int id) {
    }
    
    public final void selectAll() {
    }
    
    public final void clearSelection() {
    }
    
    public final void setManualUrl(int exerciseId, @org.jetbrains.annotations.NotNull()
    java.lang.String url) {
    }
    
    public final void importSelected() {
    }
    
    public final void dismissResult() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/gym/feature/home/presentation/video/BulkImportViewModel$Companion;", "", "()V", "TAG", "", "feature_home_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}