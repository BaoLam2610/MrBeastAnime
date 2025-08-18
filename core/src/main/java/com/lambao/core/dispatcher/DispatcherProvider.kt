package com.lambao.core.dispatcher

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 *  A data class representing dispatchers for different contexts.
 *  @param ioDispatcher The [CoroutineDispatcher] for IO-bound operations (defaults to [Dispatchers.IO]).
 *  @param defaultDispatcher The [CoroutineDispatcher] for general-purpose operations (defaults to [Dispatchers.Default]).
 *  @param mainDispatcher The [CoroutineDispatcher] for UI-related operations (defaults to [Dispatchers.Main]).
 *
 * */
data class DispatcherProvider(
    val ioDispatcher: CoroutineDispatcher,
    val defaultDispatcher: CoroutineDispatcher,
    val mainDispatcher: CoroutineDispatcher
) {
    companion object {
        /**
         * Default dispatcher provider with standard coroutine dispatchers.
         */
        val Default = DispatcherProvider(
            ioDispatcher = Dispatchers.IO,
            defaultDispatcher = Dispatchers.Default,
            mainDispatcher = Dispatchers.Main
        )
    }
}
