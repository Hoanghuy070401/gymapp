package com.gym.core.base;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u0002*\b\b\u0001\u0010\u0003*\u00020\u0004*\b\b\u0002\u0010\u0005*\u00020\u00062\u00020\u0007B\u0005\u00a2\u0006\u0002\u0010\bJ\r\u0010 \u001a\u00028\u0000H&\u00a2\u0006\u0002\u0010\u0015J\u0015\u0010!\u001a\u00020\"2\u0006\u0010\u0012\u001a\u00028\u0001H&\u00a2\u0006\u0002\u0010#J\u0016\u0010$\u001a\u00020\"2\f\u0010%\u001a\b\u0012\u0004\u0012\u00028\u00020&H\u0004J\u0013\u0010\'\u001a\u00020\"2\u0006\u0010\u0012\u001a\u00028\u0001\u00a2\u0006\u0002\u0010#J!\u0010(\u001a\u00020\"2\u0017\u0010)\u001a\u0013\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00000*\u00a2\u0006\u0002\b+H\u0004J\b\u0010,\u001a\u00020\"H\u0002R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00028\u00010\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00020\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00028\u00010\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0013\u001a\u00028\u00008BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0018\u001a\u00020\u00198BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u0017\u0010\u001c\u001a\b\u0012\u0004\u0012\u00028\u00000\u001d\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001f\u00a8\u0006-"}, d2 = {"Lcom/gym/core/base/BaseViewModel;", "State", "Lcom/gym/core/base/ViewState;", "Event", "Lcom/gym/core/base/ViewEvent;", "Effect", "Lcom/gym/core/base/ViewEffect;", "Landroidx/lifecycle/ViewModel;", "()V", "_effect", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "_event", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "effect", "Lkotlinx/coroutines/flow/SharedFlow;", "getEffect", "()Lkotlinx/coroutines/flow/SharedFlow;", "event", "initialState", "getInitialState", "()Lcom/gym/core/base/ViewState;", "initialState$delegate", "Lkotlin/Lazy;", "tag", "", "getTag", "()Ljava/lang/String;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "createInitialState", "handleEvent", "", "(Lcom/gym/core/base/ViewEvent;)V", "setEffect", "builder", "Lkotlin/Function0;", "setEvent", "setState", "reduce", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "subscribeEvents", "core_debug"})
public abstract class BaseViewModel<State extends com.gym.core.base.ViewState, Event extends com.gym.core.base.ViewEvent, Effect extends com.gym.core.base.ViewEffect> extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy initialState$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<State> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<State> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableSharedFlow<Event> _event = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.SharedFlow<Event> event = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableSharedFlow<Effect> _effect = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.SharedFlow<Effect> effect = null;
    
    public BaseViewModel() {
        super();
    }
    
    private final java.lang.String getTag() {
        return null;
    }
    
    private final State getInitialState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract State createInitialState();
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<State> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.SharedFlow<Effect> getEffect() {
        return null;
    }
    
    private final void subscribeEvents() {
    }
    
    public abstract void handleEvent(@org.jetbrains.annotations.NotNull()
    Event event);
    
    public final void setEvent(@org.jetbrains.annotations.NotNull()
    Event event) {
    }
    
    protected final void setState(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super State, ? extends State> reduce) {
    }
    
    protected final void setEffect(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<? extends Effect> builder) {
    }
}