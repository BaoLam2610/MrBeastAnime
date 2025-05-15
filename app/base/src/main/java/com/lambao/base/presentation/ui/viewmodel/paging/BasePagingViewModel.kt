package com.lambao.base.presentation.ui.viewmodel.paging

import androidx.lifecycle.viewModelScope
import com.lambao.base.data.Resource
import com.lambao.base.data.remote.paging.Paging
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class BasePagingViewModel(
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {

    /**
     * Handles a [Flow] of [Resource] without emitting a loading state.
     * Updates the [screenState] and invokes callbacks based on the resource state.
     *
     * @param flowUseCase The [Flow] emitting [Resource] objects.
     * @param onPaging Optional callback invoked with the [Paging] data when the resource is successful.
     * @param onError Optional callback invoked when an error occurs.
     * @param onSuccess Callback invoked with the data when the resource is successful.
     */
    protected fun <T> handleDataPagingNoLoading(
        flowUseCase: Flow<Resource<T>>,
        onPaging: ((Paging) -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null,
        onSuccess: suspend (T) -> Unit
    ) {
        flowUseCase.onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.paging?.let {
                        onPaging?.invoke(it)
                    }
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
     * @param onPaging Optional callback invoked with the [Paging] data when the resource is successful.
     * @param onError Optional callback invoked when an error occurs.
     * @param onSuccess Callback invoked with the data when the resource is successful.
     */
    protected fun <T> handleDataPaging(
        flowUseCase: Flow<Resource<T>>,
        onPaging: ((Paging) -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null,
        onSuccess: suspend (T) -> Unit
    ) {
        flowUseCase.onEach { resource ->
            when (resource) {
                is Resource.Loading -> setLoadingScreenState()
                is Resource.Success -> {
                    resource.paging?.let {
                        onPaging?.invoke(it)
                    }
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
     * @param onPaging Optional callback invoked with the [Paging] data when the resource is successful.
     * @param onError Optional callback invoked when any flow emits an error.
     * @param onResults Callback invoked with the successful data from both flows.
     */
    protected fun <T1, T2> handleMultiDataPaging(
        flow1: Flow<Resource<T1>>,
        flow2: Flow<Resource<T2>>,
        onPaging: ((Paging, Paging) -> Unit)? = null,
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
                    val paging1 = resource1.paging
                    val paging2 = resource2.paging
                    if (paging1 != null && paging2 != null) {
                        onPaging?.invoke(paging1, paging2)
                    }
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
     * @param onPaging Optional callback invoked with the [Paging] data when the resource is successful.
     * @param onError Optional callback invoked when any flow emits an error.
     * @param onResults Callback invoked with the successful data from all flows.
     */
    protected fun <T1, T2, T3> handleMultiDataPaging(
        flow1: Flow<Resource<T1>>,
        flow2: Flow<Resource<T2>>,
        flow3: Flow<Resource<T3>>,
        onPaging: ((Paging, Paging, Paging) -> Unit)? = null,
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
                    val paging1 = resource1.paging
                    val paging2 = resource2.paging
                    val paging3 = resource3.paging
                    if (paging1 != null && paging2 != null && paging3 != null) {
                        onPaging?.invoke(paging1, paging2, paging3)
                    }
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
     * @param onPaging Optional callback invoked with the [Paging] data when the resource is successful.
     * @param onError Optional callback invoked when any flow emits an error.
     * @param onResults Callback invoked with the list of successful data.
     */
    protected fun handleMultiDataPaging(
        vararg flows: Flow<Resource<*>>,
        onPaging: ((List<Paging>) -> Unit)? = null,
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
                val pagingList = resources.mapNotNull { (it as Resource.Success).paging }
                if (pagingList.isNotEmpty()) {
                    onPaging?.invoke(pagingList)
                }
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
     * @param onPaging Optional callback invoked with the [Paging] data when the resource is successful.
     * @param onResults Callback invoked with the list of successful data.
     */
    protected fun handleMultiDataPagingIgnoreErrors(
        vararg flows: Flow<Resource<*>>,
        onPaging: ((List<Paging>) -> Unit)? = null,
        onResults: (List<*>) -> Unit
    ) {
        combine(flows.toList()) { resources ->
            resources.toList()
        }.onEach { resources ->
            setLoadingScreenState()
            val successData = resources
                .filterIsInstance<Resource.Success<*>>()
                .mapNotNull { it.data }
            val pagingList = resources.mapNotNull { (it as Resource.Success).paging }
            if (pagingList.isNotEmpty()) {
                onPaging?.invoke(pagingList)
            }
            if (successData.isNotEmpty()) {
                setSuccessScreenState()
                onResults(successData)
            } else {
                setIdleScreenState()
            }
        }.launchIn(viewModelScope)
    }
}