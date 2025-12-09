package com.lambao.base.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lambao.base.data.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Base PagingSource class for handling pagination with the existing API structure.
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

    /**
     * Load data for the given load parameters.
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        // Get the current page from the load parameters or use 1 as the default (first page)
        val page = params.key ?: 1

        // Generate API parameters for the current page
        val apiParams = if (page == 1) initialParams else getNextPageParams(initialParams, page)

        // Load data from the API
        return try {
            var finalResult: LoadResult<Int, T>? = null

            // Collect all states until we get a Success or Error
            loadPage(apiParams)
                .collect { response ->
                    when (response) {
                        is Resource.Success -> {
                            val data = response.data ?: emptyList()
                            val pagination = response.paging

                            // Calculate the next page key based on pagination info
                            val nextPageKey = if (pagination?.hasNextPage == true) {
                                (pagination.currentPage ?: page) + 1
                            } else {
                                null
                            }

                            // Set the final result
                            finalResult = LoadResult.Page(
                                data = data,
                                prevKey = if (page > 1) page - 1 else null,
                                nextKey = nextPageKey
                            )
                        }

                        is Resource.Error -> {
                            // Handle error case
                            finalResult = LoadResult.Error(
                                response.throwable ?: Exception(
                                    response.message ?: "Unknown error"
                                )
                            )
                        }

                        // For Loading and Init states, we wait for the next emission
                        else -> Unit
                    }
                }

            // Return the final result or an error if no terminal state was reached
            finalResult
                ?: LoadResult.Error(Exception("Flow completed without emitting Success or Error"))

        } catch (e: Exception) {
            // Handle any exceptions that might occur
            LoadResult.Error(e)
        }
    }

    /**
     * Get the refresh key for the PagingState.
     * This is called when the list needs to be refreshed.
     */
    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here since prevKey == nextKey == null if the list is empty.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}