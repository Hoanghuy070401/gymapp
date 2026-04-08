package com.gym.feature.home.presentation.video;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0007\u0018\u0000 &2\u00020\u0001:\u0001&B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018J\u000e\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018J\u000e\u0010\u001a\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018J\u000e\u0010\u001b\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018J\u000e\u0010\u001c\u001a\u00020\u00162\u0006\u0010\u001d\u001a\u00020\u0018J\u000e\u0010\u001e\u001a\u00020\u00162\u0006\u0010\u001f\u001a\u00020\u0018J\u000e\u0010 \u001a\u00020\u00162\u0006\u0010!\u001a\u00020\u0018J\u000e\u0010\"\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018J\u0006\u0010#\u001a\u00020\u0016J\b\u0010$\u001a\u00020%H\u0002R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\t0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\f0\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\'"}, d2 = {"Lcom/gym/feature/home/presentation/video/AddVideoViewModel;", "Landroidx/lifecycle/ViewModel;", "videoRepository", "Lcom/gym/feature/home/data/VideoRepository;", "youtubeValidator", "Lcom/gym/feature/home/data/importer/YoutubeVideoValidator;", "(Lcom/gym/feature/home/data/VideoRepository;Lcom/gym/feature/home/data/importer/YoutubeVideoValidator;)V", "_form", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/gym/feature/home/presentation/video/AddVideoFormState;", "_uiEvent", "Lkotlinx/coroutines/channels/Channel;", "Lcom/gym/feature/home/presentation/video/AddVideoUiEvent;", "form", "Lkotlinx/coroutines/flow/StateFlow;", "getForm", "()Lkotlinx/coroutines/flow/StateFlow;", "uiEvent", "Lkotlinx/coroutines/flow/Flow;", "getUiEvent", "()Lkotlinx/coroutines/flow/Flow;", "onDescriptionChange", "", "value", "", "onDurationChange", "onLevelChange", "onTitleChange", "onToggleAge", "age", "onToggleBMI", "bmi", "onToggleGoal", "goal", "onUrlChange", "submit", "validate", "", "Companion", "feature_home_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class AddVideoViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.gym.feature.home.data.VideoRepository videoRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.gym.feature.home.data.importer.YoutubeVideoValidator youtubeValidator = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.gym.feature.home.presentation.video.AddVideoFormState> _form = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.gym.feature.home.presentation.video.AddVideoFormState> form = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.channels.Channel<com.gym.feature.home.presentation.video.AddVideoUiEvent> _uiEvent = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<com.gym.feature.home.presentation.video.AddVideoUiEvent> uiEvent = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "AddVideoViewModel";
    @org.jetbrains.annotations.NotNull()
    public static final com.gym.feature.home.presentation.video.AddVideoViewModel.Companion Companion = null;
    
    @javax.inject.Inject()
    public AddVideoViewModel(@org.jetbrains.annotations.NotNull()
    com.gym.feature.home.data.VideoRepository videoRepository, @org.jetbrains.annotations.NotNull()
    com.gym.feature.home.data.importer.YoutubeVideoValidator youtubeValidator) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.gym.feature.home.presentation.video.AddVideoFormState> getForm() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.gym.feature.home.presentation.video.AddVideoUiEvent> getUiEvent() {
        return null;
    }
    
    public final void onUrlChange(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    public final void onTitleChange(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    public final void onDescriptionChange(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    public final void onDurationChange(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    public final void onLevelChange(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    public final void onToggleAge(@org.jetbrains.annotations.NotNull()
    java.lang.String age) {
    }
    
    public final void onToggleGoal(@org.jetbrains.annotations.NotNull()
    java.lang.String goal) {
    }
    
    public final void onToggleBMI(@org.jetbrains.annotations.NotNull()
    java.lang.String bmi) {
    }
    
    private final boolean validate() {
        return false;
    }
    
    public final void submit() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/gym/feature/home/presentation/video/AddVideoViewModel$Companion;", "", "()V", "TAG", "", "feature_home_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}