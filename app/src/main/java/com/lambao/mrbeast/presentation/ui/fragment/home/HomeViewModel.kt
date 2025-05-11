package com.lambao.mrbeast.presentation.ui.fragment.home

import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.BaseViewModel
import com.lambao.mrbeast.data.remote.params.top_anime.TopAnimeParams
import com.lambao.mrbeast.domain.model.display.DisplayTopAnimeInfo
import com.lambao.mrbeast.domain.usecase.GetTopAnimeUseCase
import com.lambao.mrbeast.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTopAnimeUseCase: GetTopAnimeUseCase,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {

    private val _topAnimeSliders = MutableStateFlow<List<DisplayTopAnimeInfo>>(emptyList())
    val topAnimeSliders = _topAnimeSliders.asStateFlow()

    fun getTopAnimeSliders() {
        handleData(
            getTopAnimeUseCase.invoke(
                TopAnimeParams(
                    type = Constants.QueryParams.Type.TV,
                    filter = Constants.QueryParams.Filter.AIRING,
                    rating = Constants.QueryParams.Rating.PG13,
                    page = "1",
                    limit = "5"
                )
            )
        ) {
            launch {
                _topAnimeSliders.emit(it)
            }
        }
    }
}