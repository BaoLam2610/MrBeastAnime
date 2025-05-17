package com.lambao.mrbeast.presentation.ui.common.view_model.empty_data

import kotlinx.coroutines.flow.StateFlow

interface EmptyDataDelegate {
    fun setShowEmptyData(isShow: Boolean)
    fun shouldShowEmptyData(): StateFlow<Boolean>
}