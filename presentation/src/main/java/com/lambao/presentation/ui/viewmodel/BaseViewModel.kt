package com.lambao.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lambao.core.dispatcher.DispatcherProvider
import com.lambao.core.types.Resource
import com.lambao.presentation.ui.event.UiEvent
import com.lambao.presentation.ui.state.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * Base ViewModel providing utilities for managing UI state and events.
 *
 * This class simplifies common ViewModel operations like handling data flows and coroutine scopes.
 *
 * @param S The type of UI state this ViewModel manages
 * @param E The type of UI events this ViewModel handles
 * @param dispatcherProvider The [DispatcherProvider] to use for coroutine dispatchers.
 * @param initialState The initial state for this ViewModel
 */
open class BaseViewModel<S : UiState, E : UiEvent>(
    private val dispatcherProvider: DispatcherProvider,
    private val initialState: S
) : ViewModel() {

    /**
     * Current UI state.
     */
    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<S> = _uiState.asStateFlow()

    /**
     * One-time events that should be handled by the UI.
     */
    private val _uiEvent = MutableSharedFlow<E>()
    val uiEvent: SharedFlow<E> = _uiEvent.asSharedFlow()

    /**
     * Update the current UI state.
     *
     * @param newState The new UI state to set
     */
    protected fun updateState(newState: S) {
        _uiState.value = newState
    }

    /**
     * Update the current UI state using a transformation function.
     *
     * @param transform Function to transform the current state
     */
    protected fun updateState(transform: (S) -> S) {
        _uiState.value = transform(_uiState.value)
    }

    /**
     * Emit a one-time UI event.
     *
     * @param event The event to emit
     */
    protected fun emitEvent(event: E) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }

    /**
     * Get the current state value.
     */
    protected fun getCurrentState(): S = _uiState.value

    /**
     * Check if the current state is in a specific state.
     */
    protected fun isInState(predicate: (S) -> Boolean): Boolean = predicate(_uiState.value)

    /**
     * Execute an action if the current state matches a condition.
     *
     * @param predicate The condition to check
     * @param action The action to execute if condition is met
     */
    protected fun executeIfInState(predicate: (S) -> Boolean, action: () -> Unit) {
        if (predicate(_uiState.value)) {
            action()
        }
    }

    /**
     * Execute an action if the current state matches a condition, with state parameter.
     *
     * @param predicate The condition to check
     * @param action The action to execute if condition is met
     */
    protected fun executeIfInState(predicate: (S) -> Boolean, action: (S) -> Unit) {
        val currentState = _uiState.value
        if (predicate(currentState)) {
            action(currentState)
        }
    }

    /**
     * Reset the state to initial state.
     */
    protected fun resetToInitialState() {
        _uiState.value = initialState
    }

    /**
     * Launch a coroutine in the ViewModel scope using the main dispatcher.
     *
     * @param block The coroutine block to execute
     */
    protected fun launch(block: suspend () -> Unit) {
        viewModelScope.launch {
            block()
        }
    }

    /**
     * Launch a coroutine in the ViewModel scope using the IO dispatcher.
     *
     * @param block The coroutine block to execute
     */
    protected fun launchIO(block: suspend () -> Unit) {
        viewModelScope.launch(dispatcherProvider.ioDispatcher) {
            block()
        }
    }

    /**
     * Launch a coroutine in the ViewModel scope using the default dispatcher.
     *
     * @param block The coroutine block to execute
     */
    protected fun launchDefault(block: suspend () -> Unit) {
        viewModelScope.launch(dispatcherProvider.defaultDispatcher) {
            block()
        }
    }

    /**
     * Handle a data flow with automatic state management.
     *
     * @param flow The data flow to handle
     * @param onSuccess Callback when data is successfully loaded
     * @param onError Callback when an error occurs
     * @param onLoading Callback when loading starts
     */
    protected fun <T> handleDataFlow(
        flow: Flow<Resource<T>>,
        onSuccess: (T) -> Unit,
        onError: (Throwable?) -> Unit,
        onLoading: () -> Unit = {}
    ) {
        flow.onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    onLoading()
                }
                is Resource.Success -> {
                    resource.data?.let { onSuccess(it) }
                }
                is Resource.Error -> {
                    onError(resource.throwable)
                }

                is Resource.Init<*> -> {}
            }
        }.catch { throwable ->
            onError(throwable)
        }.launchIn(viewModelScope)
    }
}
