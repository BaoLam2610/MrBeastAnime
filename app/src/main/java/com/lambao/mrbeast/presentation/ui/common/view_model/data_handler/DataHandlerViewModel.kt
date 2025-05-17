package com.lambao.mrbeast.presentation.ui.common.view_model.data_handler

import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DataHandlerViewModel @Inject constructor(
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider), DataHandlerDelegate {
    private val _shouldShowEmptyData = MutableStateFlow(false)

    private val _shouldLoadData = MutableStateFlow(true)

    override fun setShowEmptyData(isShow: Boolean) {
        launch { _shouldShowEmptyData.emit(isShow) }
    }

    override fun setLoadData(isLoad: Boolean) {
        launch { _shouldLoadData.emit(isLoad) }
    }

    override fun shouldShowEmptyData(): StateFlow<Boolean> = _shouldShowEmptyData

    override fun shouldLoadData(): StateFlow<Boolean> = _shouldLoadData
}