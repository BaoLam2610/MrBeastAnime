package com.lambao.base.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lambao.base.data.Resource
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.state.ScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * Base ViewModel providing utilities for managing screen state and launching coroutines with configurable dispatchers.
 * This class simplifies common ViewModel operations like handling data flows, screen states, and coroutine scopes.
 * @param dispatcherProvider The [DispatcherProvider] to use for coroutine dispatchers.
 *
 */
open class BaseViewModel(
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _screenState = MutableSharedFlow<ScreenState>()
    val screenState: SharedFlow<ScreenState> get() = _screenState

    private val _screenStateFlow = MutableStateFlow<ScreenState>(ScreenState.Idle())
    val screenStateFlow get() = _screenStateFlow

    private val _shouldShowLoadingState = _screenStateFlow.map {
        it is ScreenState.Loading
    }.stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun shouldShowLoadingState(): StateFlow<Boolean> = _shouldShowLoadingState

    /**
     * Sets the screen state to the specified [ScreenState].
     *
     * @param state The [ScreenState] to set (e.g., Idle, Loading, Success, Error).
     */
    open fun setScreenState(state: ScreenState) {
        _screenStateFlow.value = state
        launch {
            delay(10)
            _screenState.emit(state)
        }
    }

    /** Sets the screen state to [ScreenState.Idle]. */
    open fun setIdleScreenState() {
        setScreenState(ScreenState.Idle())
    }

    /** Sets the screen state to [ScreenState.Loading]. */
    open fun setLoadingScreenState() {
        setScreenState(ScreenState.Loading())
    }

    /** Sets the screen state to [ScreenState.Success]. */
    open fun setSuccessScreenState() {
        setScreenState(ScreenState.Success())
    }

    /**
     * Sets the screen state to [ScreenState.Error] with the provided [throwable].
     *
     * @param throwable The error cause to associate with the error state.
     */
    open fun setErrorScreenState(throwable: Throwable) {
        setScreenState(ScreenState.Error(throwable))
    }

    fun isIdleScreenState() = _screenStateFlow.value is ScreenState.Idle

    fun isLoadingScreenState() = screenStateFlow.value is ScreenState.Loading

    fun isSuccessScreenState() = screenStateFlow.value is ScreenState.Success

    fun isErrorScreenState() = screenStateFlow.value is ScreenState.Error

    /**
     * Provides the default error message when data is null.
     * Subclasses can override this to customize the message.
     *
     * @return The default message for null data errors.
     */
    protected open fun getDataIsNullMessage(): String = "Data is null"

    /**
     * Provides the default error message for unknown errors.
     * Subclasses can override this to customize the message.
     *
     * @return The default message for unknown errors.
     */
    protected open fun getUnknownErrorMessage(): String = "Unknown error"

    /**
     * Launches a coroutine in the [viewModelScope] using the IO dispatcher.
     * Suitable for IO-bound operations like network calls or database access.
     *
     * @param block The suspend function to execute within the coroutine scope.
     * @return A [Job] representing the launched coroutine.
     */
    protected fun launchIo(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(dispatcherProvider.ioDispatcher) {
            block()
        }
    }

    /**
     * Launches a coroutine in the [viewModelScope] using the default dispatcher.
     * Suitable for general-purpose operations like computations.
     *
     * @param block The suspend function to execute within the coroutine scope.
     * @return A [Job] representing the launched coroutine.
     */
    protected fun launchDefault(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(dispatcherProvider.defaultDispatcher) {
            block()
        }
    }

    /**
     * Launches a coroutine in the [viewModelScope] using the main dispatcher.
     * Suitable for updating UI state or interacting with Android UI components.
     *
     * @param block The suspend function to execute within the coroutine scope.
     * @return A [Job] representing the launched coroutine.
     */
    protected fun launch(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(dispatcherProvider.mainDispatcher) {
            block()
        }
    }

    /**
     * Handles a [Flow] of [Resource] without emitting a loading state.
     * Updates the [screenState] and invokes callbacks based on the resource state.
     *
     * @param flowUseCase The [Flow] emitting [Resource] objects.
     * @param onError Optional callback invoked when an error occurs.
     * @param onSuccess Callback invoked with the data when the resource is successful.
     */
    protected fun <T> handleDataNoLoading(
        flowUseCase: Flow<Resource<T>>,
        onError: ((Throwable) -> Unit)? = null,
        onSuccess: suspend (T) -> Unit
    ) {
        flowUseCase.onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let {
                        setSuccessScreenState()
                        onSuccess(it)
                    } ?: run {
                        val error = Exception(getDataIsNullMessage())
                        setErrorScreenState(error)
                        onError?.invoke(error)
                    }
                }

                is Resource.Error -> {
                    val throwable = resource.throwable ?: Exception(getUnknownErrorMessage())
                    setErrorScreenState(throwable)
                    onError?.invoke(throwable)
                }

                else -> setIdleScreenState()
            }
        }.launchIn(viewModelScope)
    }

    /**
     * Handles a [Flow] of [Resource] with loading state support.
     * Updates the [screenState] and invokes callbacks based on the resource state.
     *
     * @param flowUseCase The [Flow] emitting [Resource] objects.
     * @param onError Optional callback invoked when an error occurs.
     * @param onSuccess Callback invoked with the data when the resource is successful.
     */
    protected fun <T> handleData(
        flowUseCase: Flow<Resource<T>>,
        onError: ((Throwable) -> Unit)? = null,
        onSuccess: suspend (T) -> Unit
    ) {
        flowUseCase.onEach { resource ->
            when (resource) {
                is Resource.Loading -> setLoadingScreenState()
                is Resource.Success -> {
                    resource.data?.let {
                        setSuccessScreenState()
                        onSuccess(it)
                    } ?: run {
                        val error = Exception(getDataIsNullMessage())
                        setErrorScreenState(error)
                        onError?.invoke(error)
                    }
                }

                is Resource.Error -> {
                    val throwable = resource.throwable ?: Exception(getUnknownErrorMessage())
                    setErrorScreenState(throwable)
                    onError?.invoke(throwable)
                }

                else -> setIdleScreenState()
            }
        }.launchIn(viewModelScope)
    }

    /**
     * Combines two [Flow]s of [Resource] with different data types and processes them when both succeed.
     * Updates the [screenState] and invokes callbacks based on the combined results.
     *
     * @param flow1 The first [Flow] emitting [Resource] objects of type [T1].
     * @param flow2 The second [Flow] emitting [Resource] objects of type [T2].
     * @param onError Optional callback invoked when any flow emits an error.
     * @param onResults Callback invoked with the successful data from both flows.
     */
    protected fun <T1, T2> handleMultiData(
        flow1: Flow<Resource<T1>>,
        flow2: Flow<Resource<T2>>,
        onError: ((Throwable) -> Unit)? = null,
        onResults: (T1, T2) -> Unit
    ) {
        combine(flow1, flow2) { resource1, resource2 ->
            Pair(resource1, resource2)
        }.onEach { (resource1, resource2) ->
            setLoadingScreenState()
            when {
                resource1 is Resource.Success && resource2 is Resource.Success -> {
                    val data1 = resource1.data
                    val data2 = resource2.data
                    if (data1 != null && data2 != null) {
                        setSuccessScreenState()
                        onResults(data1, data2)
                    } else {
                        val error = Exception(getDataIsNullMessage())
                        setErrorScreenState(error)
                        onError?.invoke(error)
                    }
                }

                resource1 is Resource.Error -> {
                    val throwable = resource1.throwable ?: Exception(getUnknownErrorMessage())
                    setErrorScreenState(throwable)
                    onError?.invoke(throwable)
                }

                resource2 is Resource.Error -> {
                    val throwable = resource2.throwable ?: Exception(getUnknownErrorMessage())
                    setErrorScreenState(throwable)
                    onError?.invoke(throwable)
                }

                else -> setLoadingScreenState()
            }
        }.launchIn(viewModelScope)
    }

    /**
     * Combines three [Flow]s of [Resource] with different data types and processes them when all succeed.
     * Updates the [screenState] and invokes callbacks based on the combined results.
     *
     * @param flow1 The first [Flow] emitting [Resource] objects of type [T1].
     * @param flow2 The second [Flow] emitting [Resource] objects of type [T2].
     * @param flow3 The third [Flow] emitting [Resource] objects of type [T3].
     * @param onError Optional callback invoked when any flow emits an error.
     * @param onResults Callback invoked with the successful data from all flows.
     */
    protected fun <T1, T2, T3> handleMultiData(
        flow1: Flow<Resource<T1>>,
        flow2: Flow<Resource<T2>>,
        flow3: Flow<Resource<T3>>,
        onError: ((Throwable) -> Unit)? = null,
        onResults: (T1, T2, T3) -> Unit
    ) {
        combine(flow1, flow2, flow3) { resource1, resource2, resource3 ->
            Triple(resource1, resource2, resource3)
        }.onEach { (resource1, resource2, resource3) ->
            setLoadingScreenState()
            when {
                resource1 is Resource.Success && resource2 is Resource.Success && resource3 is Resource.Success -> {
                    val data1 = resource1.data
                    val data2 = resource2.data
                    val data3 = resource3.data
                    if (data1 != null && data2 != null && data3 != null) {
                        setSuccessScreenState()
                        onResults(data1, data2, data3)
                    } else {
                        val error = Exception(getDataIsNullMessage())
                        setErrorScreenState(error)
                        onError?.invoke(error)
                    }
                }

                resource1 is Resource.Error -> {
                    val throwable = resource1.throwable ?: Exception(getUnknownErrorMessage())
                    setErrorScreenState(throwable)
                    onError?.invoke(throwable)
                }

                resource2 is Resource.Error -> {
                    val throwable = resource2.throwable ?: Exception(getUnknownErrorMessage())
                    setErrorScreenState(throwable)
                    onError?.invoke(throwable)
                }

                resource3 is Resource.Error -> {
                    val throwable = resource3.throwable ?: Exception(getUnknownErrorMessage())
                    setErrorScreenState(throwable)
                    onError?.invoke(throwable)
                }

                else -> setLoadingScreenState()
            }
        }.launchIn(viewModelScope)
    }

    /**
     * Combines multiple [Flow]s of [Resource] and processes them when all succeed.
     * Updates the [screenState] and invokes callbacks based on the combined results.
     *
     * @param flows Vararg of [Flow]s emitting [Resource] objects.
     * @param onError Optional callback invoked when any flow emits an error.
     * @param onResults Callback invoked with the list of successful data.
     */
    protected fun handleMultiData(
        vararg flows: Flow<Resource<*>>,
        onError: ((Throwable) -> Unit)? = null,
        onResults: (List<*>) -> Unit
    ) {
        combine(flows.toList()) { resources ->
            resources.toList()
        }.onEach { resources ->
            setLoadingScreenState()
            val allSuccess = resources.all { it is Resource.Success }
            if (allSuccess) {
                val dataList = resources.mapNotNull { (it as Resource.Success).data }
                if (dataList.isNotEmpty()) {
                    setSuccessScreenState()
                    onResults(dataList)
                } else {
                    val error = Exception(getDataIsNullMessage())
                    setErrorScreenState(error)
                    onError?.invoke(error)
                }
            } else {
                resources.firstOrNull { it is Resource.Error }?.let { errorResource ->
                    val throwable = (errorResource as Resource.Error).throwable
                        ?: Exception(getUnknownErrorMessage())
                    setErrorScreenState(throwable)
                    onError?.invoke(throwable)
                }
            }
        }.launchIn(viewModelScope)
    }

    /**
     * Combines a list of [Flow]s of [Resource] with potentially different data types and processes successful data, ignoring errors.
     * Updates the [screenState] and invokes the callback with successful data.
     *
     * @param flows Vararg of [Flow]s emitting [Resource] objects.
     * @param onResults Callback invoked with the list of successful data.
     */
    protected fun handleMultiDataIgnoreErrors(
        vararg flows: Flow<Resource<*>>,
        onResults: (List<*>) -> Unit
    ) {
        combine(flows.toList()) { resources ->
            resources.toList()
        }.onEach { resources ->
            setLoadingScreenState()
            val successData = resources
                .filterIsInstance<Resource.Success<*>>()
                .mapNotNull { it.data }
            if (successData.isNotEmpty()) {
                setSuccessScreenState()
                onResults(successData)
            } else {
                setIdleScreenState()
            }
        }.launchIn(viewModelScope)
    }
}