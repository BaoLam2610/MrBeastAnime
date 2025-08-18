package com.lambao.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lambao.data.core.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Base PagingSource class for handling pagination with the existing API structure.
 *
 * This class provides a foundation for implementing pagination in your app,
 * handling common pagination logic and error cases.
 *
 * @param T Type of the data being loaded
 * @param P Type of parameter object used for API calls
 * @param initialParams Initial parameters for the first page load
 * @param loadPage Function that loads a page from the API
 * @param getNextPageParams Function that calculates parameters for the next page
 */
abstract class BasePagingSource<P : Any, T : Any>(
    private val initialParams: P,
    private val loadPage: suspend (P) -> Flow<Resource<List<T>>>,
    private val getNextPageParams: (P, Int) -> P
) : PagingSource<Int, T>() {

    companion object {
        private const val FIRST_PAGE = 1
        private const val PREVIOUS_PAGE_OFFSET = 1
    }

    /**
     * Load data for the given load parameters.
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val page = params.key ?: FIRST_PAGE
        val apiParams = getApiParamsForPage(page)
        
        return try {
            loadPageData(apiParams, page)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    /**
     * Get API parameters for the specified page.
     */
    private fun getApiParamsForPage(page: Int): P {
        return if (page == FIRST_PAGE) {
            initialParams
        } else {
            getNextPageParams(initialParams, page)
        }
    }

    /**
     * Load page data and handle the response.
     */
    private suspend fun loadPageData(apiParams: P, page: Int): LoadResult<Int, T> {
        var finalResult: LoadResult<Int, T>? = null

        loadPage(apiParams).collect { response ->
            finalResult = when (response) {
                is Resource.Success -> createSuccessResult(response, page)
                is Resource.Error -> createErrorResult(response)
                else -> null // For Loading and Init states, continue collecting
            }
        }

        return finalResult ?: createFlowCompletionError()
    }

    /**
     * Create a successful LoadResult from the response.
     */
    private fun createSuccessResult(response: Resource.Success<List<T>>, page: Int): LoadResult<Int, T> {
        val data = response.data ?: emptyList()
        val pagination = response.paging
        val nextPageKey = calculateNextPageKey(pagination, page)

        return LoadResult.Page(
            data = data,
            prevKey = if (page > FIRST_PAGE) page - PREVIOUS_PAGE_OFFSET else null,
            nextKey = nextPageKey
        )
    }

    /**
     * Calculate the next page key based on pagination information.
     */
    private fun calculateNextPageKey(pagination: Paging?, page: Int): Int? {
        return if (pagination?.hasNextPage == true) {
            (pagination.currentPage ?: page) + PREVIOUS_PAGE_OFFSET
        } else {
            null
        }
    }

    /**
     * Create an error LoadResult from the response.
     */
    private fun createErrorResult(response: Resource.Error<List<T>>): LoadResult<Int, T> {
        return LoadResult.Error(
            response.throwable ?: Exception(response.message ?: "Unknown error")
        )
    }

    /**
     * Create an error when the flow completes without a terminal state.
     */
    private fun createFlowCompletionError(): LoadResult<Int, T> {
        return LoadResult.Error(
            Exception("Flow completed without emitting Success or Error")
        )
    }

    /**
     * Get the refresh key for the PagingState.
     * 
     * This is called when the list needs to be refreshed.
     */
    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(PREVIOUS_PAGE_OFFSET) 
                ?: anchorPage?.nextKey?.minus(PREVIOUS_PAGE_OFFSET)
        }
    }
}
