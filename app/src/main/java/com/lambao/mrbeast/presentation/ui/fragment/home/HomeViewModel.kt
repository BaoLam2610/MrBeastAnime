package com.lambao.mrbeast.presentation.ui.fragment.home

import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.BaseViewModel
import com.lambao.mrbeast.data.remote.request.top_anime.TopAnimeRequest
import com.lambao.mrbeast.domain.usecase.GetTopAnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTopAnimeUseCase: GetTopAnimeUseCase,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {
    private val _data = MutableSharedFlow<String>()
    val data get() = _data.asSharedFlow()

    fun setData(string: String) {
        launch {
            _data.emit(string)
        }
    }

    fun getTopAnime() {
        handleData(
            getTopAnimeUseCase.invoke(
                TopAnimeRequest(
                    type = "",
                    filter = "",
                    rating = "",
                    sfw = "",
                    page = "0",
                    limit = "-"
                )
            )
        ) {

        }
    }
}