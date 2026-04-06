package com.gym.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

interface ViewState
interface ViewEvent
interface ViewEffect

abstract class BaseViewModel<State : ViewState, Event : ViewEvent, Effect : ViewEffect> : ViewModel() {

    /** Each subclass is used as the log tag for easy Logcat filtering. */
    private val tag: String get() = this::class.java.simpleName

    private val initialState: State by lazy { createInitialState() }
    abstract fun createInitialState(): State

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    private val event: SharedFlow<Event> = _event.asSharedFlow()

    private val _effect: MutableSharedFlow<Effect> = MutableSharedFlow()
    val effect: SharedFlow<Effect> = _effect.asSharedFlow()

    init {
        subscribeEvents()
    }

    private fun subscribeEvents() {
        viewModelScope.launch {
            event.collect { event ->
                GymLogger.d(tag, "handleEvent ← $event")
                try {
                    handleEvent(event)
                } catch (e: Exception) {
                    GymLogger.e(tag, e, "Unhandled exception in handleEvent for $event")
                }
            }
        }
    }

    abstract fun handleEvent(event: Event)

    fun setEvent(event: Event) {
        GymLogger.d(tag, "setEvent → $event")
        viewModelScope.launch { _event.emit(event) }
    }

    protected fun setState(reduce: State.() -> State) {
        val newState = uiState.value.reduce()
        GymLogger.d(tag, "setState → $newState")
        _uiState.value = newState
    }

    protected fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        GymLogger.d(tag, "setEffect → $effectValue")
        viewModelScope.launch { _effect.emit(effectValue) }
    }
}
