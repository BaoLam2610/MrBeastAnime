package com.lambao.domain.base

import com.lambao.core.dispatcher.DispatcherProvider
import kotlinx.coroutines.withContext

/**
 * Base class for use cases that perform suspend operations.
 * 
 * This class provides common functionality for use cases that need to
 * perform asynchronous operations using suspend functions.
 *
 * @param dispatcherProvider Provider for coroutine dispatchers
 * @param Params Input parameters for the use case (can be null)
 * @param Result Output result type
 */
abstract class SuspendUseCase<in Params, out Result>(
    dispatcherProvider: DispatcherProvider
) : UseCase(dispatcherProvider) {
    
    /**
     * Execute the use case with the given parameters.
     * 
     * @param params Input parameters (can be null)
     * @return Result of the operation
     */
    protected abstract suspend fun execute(params: Params? = null): Result

    /**
     * Invoke the use case with the given parameters.
     * 
     * @param params Input parameters (can be null)
     * @return Result of the operation executed on the appropriate dispatcher
     */
    suspend operator fun invoke(params: Params? = null): Result = withContext(coroutineDispatcher) {
        execute(params)
    }
}
