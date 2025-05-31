package com.lambao.mrbeast.presentation.ui.fragment.tops

import androidx.paging.map
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.mrbeast.data.remote.params.top.TopParams
import com.lambao.mrbeast.domain.model.display.DisplayTopAnimeInfo
import com.lambao.mrbeast.domain.usecase.top.GetTopAnimePagingUseCase
import com.lambao.mrbeast.presentation.ui.fragment.base.anime_list.AnimeListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class TopAnimeListViewModel @Inject constructor(
    private val getTopAnimePagingUseCase: GetTopAnimePagingUseCase,
    dispatcherProvider: DispatcherProvider
) : AnimeListViewModel<DisplayTopAnimeInfo>(dispatcherProvider) {

    fun getTopAnimePaginated() = getPagingData {
        getTopAnimePagingUseCase.invoke(
            TopParams(
                type = getType().value,
                filter = getFilter().value,
                rating = getRating().value,
                sfw = getSfw().value,
                page = 1,
                limit = 20
            )
        ).catch { setErrorScreenState(it) }
            .map {
                it.map { item -> item as DisplayTopAnimeInfo }
            }
    }
}