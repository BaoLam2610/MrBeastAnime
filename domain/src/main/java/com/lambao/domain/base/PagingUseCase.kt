package com.lambao.domain.base

import androidx.paging.PagingData
import com.lambao.core.dispatcher.DispatcherProvider

/**
 * Base class for use cases that return paged results.
 *
 * This class provides common functionality for use cases that need to
 * return paginated data using Android Paging 3.
 *
 * @param dispatcherProvider Provider for coroutine dispatchers
 * @param Params Input parameters for the use case
 * @param Result Output result type (must be Any to satisfy PagingData requirements)
 */
abstract class PagingUseCase<Params, Result : Any>(
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<Params, PagingData<Result>>(dispatcherProvider)
