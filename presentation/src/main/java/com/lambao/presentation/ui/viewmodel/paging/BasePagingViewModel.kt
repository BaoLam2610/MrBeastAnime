package com.lambao.presentation.ui.viewmodel.paging

import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.lambao.core.dispatcher.DispatcherProvider
import com.lambao.presentation.ui.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * Base ViewModel for Android Paging 3 functionality.
 * 
 * This class extends BaseViewModel to provide automatic paging capabilities
 * for ViewModels that need to handle Android Paging 3 library.
 *
 * @param T The type of data items in the paging list
 * @param dispatcherProvider The dispatcher provider for coroutine operations
 */
abstract class BasePagingViewModel<T : Any>(
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {

    // Cached PagingData flow to survive configuration changes
    private var _currentPagingData: Flow<PagingData<T>>? = null

    // States for tracking UI events
    private val _isEmpty = MutableStateFlow(false)
    val isEmpty: StateFlow<Boolean> get() = _isEmpty

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> get() = _isRefreshing

    private val _shouldRefreshPage = MutableSharedFlow<Unit>()
    val shouldRefreshPage = _shouldRefreshPage.asSharedFlow()

    /**
     * Get the PagingData flow, creating it if it doesn't exist.
     * Caches the flow in the ViewModel to survive configuration changes.
     *
     * @param createPagingFlow Function to create a new PagingData flow if needed
     * @return Flow of PagingData
     */
    protected fun getPagingData(
        createPagingFlow: () -> Flow<PagingData<T>>
    ): Flow<PagingData<T>> {
        val currentPagingData = _currentPagingData
        return if (currentPagingData != null) {
            currentPagingData
        } else {
            val newPagingData = createPagingFlow().cachedIn(viewModelScope)
            _currentPagingData = newPagingData
            newPagingData
        }
    }

    /**
     * Invalidate the current PagingData flow to force a reload from the data source.
     */
    fun invalidatePagingData() {
        _currentPagingData = null
        _isRefreshing.value = false
    }

    /**
     * Trigger a refresh of the current page.
     */
    fun triggerRefreshPage() {
        launch {
            _shouldRefreshPage.emit(Unit)
        }
    }

    /**
     * Handle load states from the PagingDataAdapter to update UI state accordingly.
     *
     * @param loadState The combined load states from the PagingDataAdapter
     * @param itemCount The current item count in the adapter
     */
    fun handleLoadStates(loadState: CombinedLoadStates, itemCount: Int) {
        val isRefreshing = loadState.refresh is LoadState.Loading
        _isRefreshing.value = isRefreshing

        // Only show loading state for initial load
        if (isRefreshing && itemCount == 0) {
            setLoadingScreenState()
        }

        // Handle errors
        val errorState = loadState.refresh as? LoadState.Error
            ?: loadState.source.append as? LoadState.Error
            ?: loadState.source.prepend as? LoadState.Error
            ?: loadState.append as? LoadState.Error
            ?: loadState.prepend as? LoadState.Error

        errorState?.let {
            if (itemCount == 0) {
                setErrorScreenState(it.error)
            }
        }

        // Handle empty state
        val isListEmpty = itemCount == 0 && loadState.refresh is LoadState.NotLoading
        _isEmpty.value = isListEmpty
        if (isListEmpty) {
            setIdleScreenState()
            return
        }

        // Set success state when data is loaded and not empty
        if (loadState.refresh is LoadState.NotLoading && itemCount > 0) {
            setSuccessScreenState()
        }
    }
}
