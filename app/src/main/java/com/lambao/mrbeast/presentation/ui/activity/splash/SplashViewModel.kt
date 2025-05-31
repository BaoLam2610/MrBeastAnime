package com.lambao.mrbeast.presentation.ui.activity.splash

import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.BaseViewModel
import com.lambao.mrbeast.domain.usecase.genres.CacheAnimeGenresUseCase
import com.lambao.mrbeast.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val cacheAnimeGenresUseCase: CacheAnimeGenresUseCase,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {

    private val _isApiCallCompleted = MutableStateFlow(false)
    val isApiCallCompleted = _isApiCallCompleted.asStateFlow()

    fun fetchAnimeGenres() {
        handleDataNoLoading(
            cacheAnimeGenresUseCase.invoke(Constants.QueryParams.Filter.GENRES),
            onError = {
                _isApiCallCompleted.value = true
            }
        ) {
            _isApiCallCompleted.value = true
        }
    }
}