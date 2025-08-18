package com.lambao.presentation.extension

import androidx.lifecycle.Lifecycle
import com.lambao.presentation.ui.activity.BaseActivity
import com.lambao.presentation.ui.bottom_sheet.BaseBottomSheet
import com.lambao.presentation.ui.dialog.BaseDialog
import com.lambao.presentation.ui.fragment.BaseFragment
import kotlinx.coroutines.flow.Flow

fun <T> BaseActivity<*>.observe(
    flow: Flow<T>,
    lifecycleState: Lifecycle.State = Lifecycle.State.CREATED,
    onChanged: suspend (T) -> Unit = {}
) {
    flow.launchCollect(this, lifecycleState) { onChanged.invoke(it) }
}

fun <T> BaseActivity<*>.observeLatest(
    flow: Flow<T>,
    lifecycleState: Lifecycle.State = Lifecycle.State.CREATED,
    onChanged: suspend (T) -> Unit = {}
) {
    flow.launchCollectLatest(this, lifecycleState) { onChanged.invoke(it) }
}

fun <T> BaseFragment<*>.observe(
    flow: Flow<T>,
    lifecycleState: Lifecycle.State = Lifecycle.State.CREATED,
    onChanged: suspend (T) -> Unit = {}
) {
    flow.launchCollect(this, lifecycleState) { onChanged.invoke(it) }
}

fun <T> BaseFragment<*>.observeLatest(
    flow: Flow<T>,
    lifecycleState: Lifecycle.State = Lifecycle.State.CREATED,
    onChanged: suspend (T) -> Unit = {}
) {
    flow.launchCollectLatest(this, lifecycleState) { onChanged.invoke(it) }
}

fun <T> BaseDialog<*>.observe(
    flow: Flow<T>,
    lifecycleState: Lifecycle.State = Lifecycle.State.CREATED,
    onChanged: suspend (T) -> Unit = {}
) {
    flow.launchCollect(this, lifecycleState) { onChanged.invoke(it) }
}

fun <T> BaseDialog<*>.observeLatest(
    flow: Flow<T>,
    lifecycleState: Lifecycle.State = Lifecycle.State.CREATED,
    onChanged: suspend (T) -> Unit = {}
) {
    flow.launchCollectLatest(this, lifecycleState) { onChanged.invoke(it) }
}

fun <T> BaseBottomSheet<*>.observe(
    flow: Flow<T>,
    lifecycleState: Lifecycle.State = Lifecycle.State.CREATED,
    onChanged: suspend (T) -> Unit = {}
) {
    flow.launchCollect(this, lifecycleState) { onChanged.invoke(it) }
}

fun <T> BaseBottomSheet<*>.observeLatest(
    flow: Flow<T>,
    lifecycleState: Lifecycle.State = Lifecycle.State.CREATED,
    onChanged: suspend (T) -> Unit = {}
) {
    flow.launchCollectLatest(this, lifecycleState) { onChanged.invoke(it) }
}


