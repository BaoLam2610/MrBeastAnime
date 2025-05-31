package com.lambao.mrbeast.presentation.ui.fragment.anime_detail.recommendations

import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.paging.RemotePagingViewModel
import com.lambao.mrbeast.data.remote.params.anime.AnimeParams
import com.lambao.mrbeast.domain.model.display.DisplayAnimeRecommendationInfo
import com.lambao.mrbeast.domain.usecase.anime.GetAnimeRecommendationsUseCase
import com.lambao.mrbeast.presentation.ui.common.view_model.anime.AnimeDelegate
import com.lambao.mrbeast.presentation.ui.common.view_model.anime.AnimeViewModel
import com.lambao.mrbeast.presentation.ui.common.view_model.data_handler.DataHandlerDelegate
import com.lambao.mrbeast.presentation.ui.common.view_model.data_handler.DataHandlerViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnimeRecommendationsViewModel @Inject constructor(
    private val getAnimeRecommendationsUseCase: GetAnimeRecommendationsUseCase,
    dispatcherProvider: DispatcherProvider
) : RemotePagingViewModel<DisplayAnimeRecommendationInfo>(dispatcherProvider),
    AnimeDelegate by AnimeViewModel(dispatcherProvider),
    DataHandlerDelegate by DataHandlerViewModel(dispatcherProvider) {

    fun fetchAnimeRecommendations(id: String) {
        setAnimeId(id)
        fetchData()
    }

    override fun fetchData() {
        handleDataPaging(
            getAnimeRecommendationsUseCase.invoke(
                AnimeParams(id = getAnimeId().value)
            ),
            onPaging = ::setPaging
        ) {
            appendItems(it)
            setShowEmptyData(items.value.isEmpty())
        }
    }
}