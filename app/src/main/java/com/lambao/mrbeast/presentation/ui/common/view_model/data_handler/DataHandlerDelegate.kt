package com.lambao.mrbeast.presentation.ui.common.view_model.data_handler

import kotlinx.coroutines.flow.StateFlow

interface DataHandlerDelegate {
    fun setShowEmptyData(isShow: Boolean)
    fun setLoadData(isLoad: Boolean)

    fun shouldShowEmptyData(): StateFlow<Boolean>
    fun shouldLoadData(): StateFlow<Boolean>
}