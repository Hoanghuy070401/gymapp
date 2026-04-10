package com.gym.feature.home.presentation.exerciselibrary;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u0010\u0013\u001a\u00020\u0014H\u0002J\u0012\u0010\u0015\u001a\u00020\u00142\b\b\u0002\u0010\u0016\u001a\u00020\tH\u0002J\u000e\u0010\u0017\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\tJ\u0010\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\tH\u0002J\u000e\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\tJ\u0006\u0010\u001b\u001a\u00020\u0014J\"\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001d2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dH\u0082@\u00a2\u0006\u0002\u0010 R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u00020\t8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006!"}, d2 = {"Lcom/gym/feature/home/presentation/exerciselibrary/ExerciseLibraryViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/gym/feature/home/data/exercisedb/ExerciseRepository;", "translatorManager", "Lcom/gym/core/translation/TranslatorManager;", "(Lcom/gym/feature/home/data/exercisedb/ExerciseRepository;Lcom/gym/core/translation/TranslatorManager;)V", "_searchQuery", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_state", "Lcom/gym/feature/home/presentation/exerciselibrary/ExerciseLibraryState;", "apiKey", "getApiKey", "()Ljava/lang/String;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "loadBodyParts", "", "loadExercises", "bodyPart", "onBodyPartSelected", "onSearch", "query", "onSearchQueryChanged", "retry", "translateExercises", "", "Lcom/gym/domain/model/ExerciseInfo;", "list", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "feature_home_debug"})
@kotlin.OptIn(markerClass = {kotlinx.coroutines.FlowPreview.class})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class ExerciseLibraryViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.gym.feature.home.data.exercisedb.ExerciseRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.gym.core.translation.TranslatorManager translatorManager = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.gym.feature.home.presentation.exerciselibrary.ExerciseLibraryState> _state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.gym.feature.home.presentation.exerciselibrary.ExerciseLibraryState> state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _searchQuery = null;
    
    @javax.inject.Inject()
    public ExerciseLibraryViewModel(@org.jetbrains.annotations.NotNull()
    com.gym.feature.home.data.exercisedb.ExerciseRepository repository, @org.jetbrains.annotations.NotNull()
    com.gym.core.translation.TranslatorManager translatorManager) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.gym.feature.home.presentation.exerciselibrary.ExerciseLibraryState> getState() {
        return null;
    }
    
    private final java.lang.String getApiKey() {
        return null;
    }
    
    public final void onSearchQueryChanged(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
    }
    
    public final void onBodyPartSelected(@org.jetbrains.annotations.NotNull()
    java.lang.String bodyPart) {
    }
    
    public final void retry() {
    }
    
    private final java.lang.Object translateExercises(java.util.List<com.gym.domain.model.ExerciseInfo> list, kotlin.coroutines.Continuation<? super java.util.List<com.gym.domain.model.ExerciseInfo>> $completion) {
        return null;
    }
    
    private final void loadBodyParts() {
    }
    
    private final void loadExercises(java.lang.String bodyPart) {
    }
    
    private final void onSearch(java.lang.String query) {
    }
}