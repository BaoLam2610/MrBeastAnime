package com.lambao.mrbeast.presentation.ui.common.view_model.empty_data

import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class EmptyDataViewModel @Inject constructor(
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider), EmptyDataDelegate {
    private val _shouldShowEmptyData = MutableStateFlow(false)

    override fun setShowEmptyData(isShow: Boolean) {
        launch { _shouldShowEmptyData }
    }

    override fun shouldShowEmptyData(): StateFlow<Boolean> = _shouldShowEmptyData
}