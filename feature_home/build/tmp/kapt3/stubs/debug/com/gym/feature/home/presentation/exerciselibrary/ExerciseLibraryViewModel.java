package com.gym.feature.home.presentation.exerciselibrary;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u000e\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u001c\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u00152\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015H\u0002J\u000e\u0010\u0018\u001a\u00020\u0019H\u0082@\u00a2\u0006\u0002\u0010\u001aJ\u0018\u0010\u001b\u001a\u00020\u00192\b\b\u0002\u0010\u001c\u001a\u00020\u000bH\u0082@\u00a2\u0006\u0002\u0010\u001dJ\u000e\u0010\u001e\u001a\u00020\u0019H\u0082@\u00a2\u0006\u0002\u0010\u001aJ\u000e\u0010\u001f\u001a\u00020\u00192\u0006\u0010\u001c\u001a\u00020\u000bJ\u0010\u0010 \u001a\u00020\u00192\u0006\u0010!\u001a\u00020\u000bH\u0002J\u000e\u0010\"\u001a\u00020\u00192\u0006\u0010!\u001a\u00020\u000bJ\u0006\u0010#\u001a\u00020\u0019J\u0006\u0010$\u001a\u00020\u0019J\"\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00160\u00152\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015H\u0082@\u00a2\u0006\u0002\u0010&R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\'"}, d2 = {"Lcom/gym/feature/home/presentation/exerciselibrary/ExerciseLibraryViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/gym/feature/home/data/exercisedb/ExerciseRepository;", "translatorManager", "Lcom/gym/core/translation/TranslatorManager;", "profileRepository", "Lcom/gym/feature/home/data/UserProfileRepository;", "(Lcom/gym/feature/home/data/exercisedb/ExerciseRepository;Lcom/gym/core/translation/TranslatorManager;Lcom/gym/feature/home/data/UserProfileRepository;)V", "_searchQuery", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_state", "Lcom/gym/feature/home/presentation/exerciselibrary/ExerciseLibraryState;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "userProfile", "Lcom/gym/feature/home/data/UserProfile;", "applyPersonalization", "", "Lcom/gym/domain/model/ExerciseInfo;", "list", "loadBodyParts", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "loadExercises", "bodyPart", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "loadUserProfile", "onBodyPartSelected", "onSearch", "query", "onSearchQueryChanged", "retry", "togglePersonalization", "translateExercises", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "feature_home_debug"})
@kotlin.OptIn(markerClass = {kotlinx.coroutines.FlowPreview.class})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class ExerciseLibraryViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.gym.feature.home.data.exercisedb.ExerciseRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.gym.core.translation.TranslatorManager translatorManager = null;
    @org.jetbrains.annotations.NotNull()
    private final com.gym.feature.home.data.UserProfileRepository profileRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.gym.feature.home.presentation.exerciselibrary.ExerciseLibraryState> _state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.gym.feature.home.presentation.exerciselibrary.ExerciseLibraryState> state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _searchQuery = null;
    @org.jetbrains.annotations.Nullable()
    private com.gym.feature.home.data.UserProfile userProfile;
    
    @javax.inject.Inject()
    public ExerciseLibraryViewModel(@org.jetbrains.annotations.NotNull()
    com.gym.feature.home.data.exercisedb.ExerciseRepository repository, @org.jetbrains.annotations.NotNull()
    com.gym.core.translation.TranslatorManager translatorManager, @org.jetbrains.annotations.NotNull()
    com.gym.feature.home.data.UserProfileRepository profileRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.gym.feature.home.presentation.exerciselibrary.ExerciseLibraryState> getState() {
        return null;
    }
    
    public final void onSearchQueryChanged(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
    }
    
    public final void onBodyPartSelected(@org.jetbrains.annotations.NotNull()
    java.lang.String bodyPart) {
    }
    
    /**
     * Bật/tắt cá nhân hoá theo tuổi & mục tiêu
     */
    public final void togglePersonalization() {
    }
    
    public final void retry() {
    }
    
    private final java.lang.Object loadUserProfile(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object loadBodyParts(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object loadExercises(java.lang.String bodyPart, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final void onSearch(java.lang.String query) {
    }
    
    private final java.util.List<com.gym.domain.model.ExerciseInfo> applyPersonalization(java.util.List<com.gym.domain.model.ExerciseInfo> list) {
        return null;
    }
    
    private final java.lang.Object translateExercises(java.util.List<com.gym.domain.model.ExerciseInfo> list, kotlin.coroutines.Continuation<? super java.util.List<com.gym.domain.model.ExerciseInfo>> $completion) {
        return null;
    }
}