package com.lambao.mrbeast.presentation.ui.activity.main

import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.BaseViewModel
import com.lambao.mrbeast_anime.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {

    private val _shouldShowBottomNav = MutableStateFlow(true)
    val shouldShowBottomNav = _shouldShowBottomNav.asStateFlow()

    fun setShowBottomNav(isShow: Boolean) {
        launch {
            _shouldShowBottomNav.emit(isShow)
        }
    }

    fun updateShowBottomNavByFragmentId(fragmentId: Int) {
        setShowBottomNav(fragmentId != R.id.animeDetailFragment)
    }
}