package com.lambao.mrbeast.presentation.ui.fragment.home

import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.BaseViewModel
import com.lambao.mrbeast.data.remote.request.top_anime.TopAnimeRequest
import com.lambao.mrbeast.domain.usecase.GetTopAnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTopAnimeUseCase: GetTopAnimeUseCase,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {
    fun getTopAnime() {
        handleData(
            getTopAnimeUseCase.invoke(
                TopAnimeRequest(
                    page = "1",
                    limit = "5"
                )
            )
        ) {

        }
    }
}