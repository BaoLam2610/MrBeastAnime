package com.lambao.domain.base

import com.lambao.core.dispatcher.DispatcherProvider

/**
 * Base class for all use cases in the domain layer.
 * 
 * This class provides common functionality for managing coroutine dispatchers
 * and serves as the foundation for all domain operations.
 *
 * @param dispatcherProvider Provider for coroutine dispatchers
 */
abstract class UseCase(
    private val dispatcherProvider: DispatcherProvider
) {
    /**
     * Use Default dispatcher for domain operations.
     * Domain operations are CPU-bound business logic, not I/O bound.
     */
    protected open val coroutineDispatcher get() = dispatcherProvider.defaultDispatcher
}
