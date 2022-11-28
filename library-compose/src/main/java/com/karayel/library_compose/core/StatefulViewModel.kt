package com.karayel.library_compose.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class StatefulViewModel<S : UiState, E : Event> constructor(initialState: S) : ViewModel() {

    private val _stateFlow = MutableStateFlow(ComposeState(initialState))
    val stateFlow: StateFlow<ComposeState<S>> = _stateFlow

    private val _eventFlow = MutableSharedFlow<E>(replay = 1)
    val eventFlow: SharedFlow<E> = _eventFlow

    val currentUiState: S
        get() = _stateFlow.value.uiState

    protected fun setState(reducer: S.() -> S) {
        _stateFlow.update {
            _stateFlow.value.copy(uiState = reducer(currentUiState))
        }
    }

    fun setProgress(showProgress: Boolean) {
        _stateFlow.update {
            _stateFlow.value.copy(progress = showProgress)
        }
    }

    fun setError(exception: Exception?) {
        _stateFlow.update {
            _stateFlow.value.copy(errorMessage = exception?.message)
        }
    }

    /**
     * Pushes a new event with [_eventFlow] from this [StatefulViewModel].
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    protected fun pushEvent(event: E) = viewModelScope.launch {
        // TODO: compare for mutex for trigger same time events.
        _eventFlow.apply {
            emit(event)
            resetReplayCache()
        }
    }
}
