package com.lambao.presentation.extension

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine

fun <T> Throwable.asFlow(): Flow<T> = flow {
    emit(suspendCancellableCoroutine { cancellableContinuation ->
        cancellableContinuation.cancel(this@asFlow)
    })
}

fun <T> Flow<T>.launchCollect(
    lifecycleOwner: LifecycleOwner,
    lifecycleState: Lifecycle.State = Lifecycle.State.CREATED,
    collect: suspend (T) -> Unit
) = lifecycleOwner.lifecycleScope.launch {
    lifecycleOwner.repeatOnLifecycle(lifecycleState) {
        collect { collect(it) }
    }
}

fun <T> Flow<T>.launchCollectLatest(
    lifecycleOwner: LifecycleOwner,
    lifecycleState: Lifecycle.State = Lifecycle.State.CREATED,
    collect: suspend (T) -> Unit
) = lifecycleOwner.lifecycleScope.launch {
    lifecycleOwner.repeatOnLifecycle(lifecycleState) {
        collectLatest { collect(it) }
    }
}

fun LifecycleOwner.launchWhen(
    lifecycleState: Lifecycle.State = Lifecycle.State.CREATED,
    block: suspend CoroutineScope.() -> Unit
) {
    lifecycleScope.launch {
        repeatOnLifecycle(lifecycleState) {
            block()
        }
    }
}

fun LifecycleOwner.launchWhenCreated(block: suspend CoroutineScope.() -> Unit) {
    launchWhen(Lifecycle.State.CREATED, block)
}

fun LifecycleOwner.launchWhenStarted(block: suspend CoroutineScope.() -> Unit) {
    launchWhen(Lifecycle.State.STARTED, block)
}

fun LifecycleOwner.launchWhenResumed(block: suspend CoroutineScope.() -> Unit) {
    launchWhen(Lifecycle.State.RESUMED, block)
}


