package com.lambao.presentation.ui.viewmodel.paging

import androidx.lifecycle.viewModelScope
import com.lambao.core.types.Resource
import com.lambao.core.types.PageInfo
import com.lambao.core.dispatcher.DispatcherProvider
import com.lambao.presentation.ui.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * Base ViewModel for manual paging functionality.
 * 
 * This class extends BaseViewModel to provide manual paging capabilities
 * for ViewModels that need to handle pagination manually.
 *
 * @param dispatcherProvider The dispatcher provider for coroutine operations
 */
abstract class BaseManualPagingViewModel(
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {

    /**
     * Handles a [Flow] of [Resource] without emitting a loading state.
     * Updates the [screenState] and invokes callbacks based on the resource state.
     *
     * @param flowUseCase The [Flow] emitting [Resource] objects.
     * @param onPaging Optional callback invoked with the [PageInfo] data when the resource is successful.
     * @param onError Optional callback invoked when an error occurs.
     * @param onSuccess Callback invoked with the data when the resource is successful.
     */
    protected fun <T> handleDataPagingNoLoading(
        flowUseCase: Flow<Resource<T>>,
        onPaging: ((PageInfo) -> Unit)? = null,
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
                    resource.pageInfo?.let {
                        onPaging?.invoke(it)
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
     * @param onPaging Optional callback invoked with the [PageInfo] data when the resource is successful.
     * @param onError Optional callback invoked when an error occurs.
     * @param onSuccess Callback invoked with the data when the resource is successful.
     */
    protected fun <T> handleDataPaging(
        flowUseCase: Flow<Resource<T>>,
        onPaging: ((PageInfo) -> Unit)? = null,
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
                    resource.pageInfo?.let {
                        onPaging?.invoke(it)
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
}
