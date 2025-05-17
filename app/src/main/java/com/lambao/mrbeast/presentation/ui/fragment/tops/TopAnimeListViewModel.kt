package com.lambao.mrbeast.presentation.ui.fragment.tops

import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.mrbeast.data.remote.params.top.TopParams
import com.lambao.mrbeast.domain.model.display.DisplayTopAnimeInfo
import com.lambao.mrbeast.domain.usecase.GetTopAnimeUseCase
import com.lambao.mrbeast.presentation.ui.fragment.base.anime_list.AnimeListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopAnimeListViewModel @Inject constructor(
    private val getTopAnimeUseCase: GetTopAnimeUseCase,
    dispatcherProvider: DispatcherProvider
) : AnimeListViewModel<DisplayTopAnimeInfo>(dispatcherProvider) {
    override fun fetchData() {
        handleDataPaging(
            getTopAnimeUseCase.invoke(
                TopParams(
                    type = getType().value,
                    filter = getFilter().value,
                    rating = getRating().value,
                    sfw = getSfw().value,
                    page = currentPage.value,
                    limit = pageSize.value
                )
            ),
            onPaging = ::setPaging,
        ) {
            appendItems(it)
            setShowEmptyData(items.value.isEmpty())
        }
    }
}