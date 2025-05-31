package com.lambao.mrbeast.presentation.ui.fragment.anime_rating.reviews

import androidx.paging.map
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.paging.BasePagingViewModel
import com.lambao.mrbeast.data.remote.params.anime.AnimeReviewParams
import com.lambao.mrbeast.domain.model.display.DisplayAnimeReviewInfo
import com.lambao.mrbeast.domain.usecase.anime.GetAnimeReviewsPagingUseCase
import com.lambao.mrbeast.presentation.ui.common.view_model.anime.AnimeDelegate
import com.lambao.mrbeast.presentation.ui.common.view_model.anime.AnimeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class AnimeReviewsViewModel @Inject constructor(
    private val getAnimeReviewsPagingUseCase: GetAnimeReviewsPagingUseCase,
    dispatcherProvider: DispatcherProvider
) : BasePagingViewModel<DisplayAnimeReviewInfo>(dispatcherProvider),
    AnimeDelegate by AnimeViewModel(dispatcherProvider) {
    fun getAnimeReviewsPaginated() = getPagingData {
        getAnimeReviewsPagingUseCase.invoke(
            AnimeReviewParams(
                id = getAnimeId().value,
                page = 1,
            )
        ).catch { setErrorScreenState(it) }
            .map {
                it.map { item -> item as DisplayAnimeReviewInfo }
            }
    }
}