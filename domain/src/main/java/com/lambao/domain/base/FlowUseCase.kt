package com.lambao.domain.base

import com.lambao.core.dispatcher.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

/**
 * Base class for use cases that return Flow results.
 * 
 * This class provides common functionality for use cases that need to
 * return reactive streams of data using Kotlin Flow.
 *
 * @param dispatcherProvider Provider for coroutine dispatchers
 * @param Params Input parameters for the use case (can be null)
 * @param Result Output result type
 */
abstract class FlowUseCase<in Params, out Result>(
    dispatcherProvider: DispatcherProvider
) : UseCase(dispatcherProvider) {
    
    /**
     * Execute the use case with the given parameters.
     * 
     * @param params Input parameters (can be null)
     * @return Flow of results
     */
    protected abstract fun execute(params: Params? = null): Flow<Result>

    /**
     * Invoke the use case with the given parameters.
     * 
     * @param params Input parameters (can be null)
     * @return Flow of results executed on the appropriate dispatcher
     */
    operator fun invoke(params: Params? = null): Flow<Result> =
        execute(params).flowOn(coroutineDispatcher)
}
