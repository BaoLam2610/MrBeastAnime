package com.lambao.mrbeast.presentation.ui.activity.main

import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {
}