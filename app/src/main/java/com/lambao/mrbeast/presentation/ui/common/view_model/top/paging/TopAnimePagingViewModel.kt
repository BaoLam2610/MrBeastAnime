package com.lambao.mrbeast.presentation.ui.common.view_model.top.paging

import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.paging.RemotePagingViewModel
import com.lambao.mrbeast.data.remote.params.top.TopParams
import com.lambao.mrbeast.domain.model.display.DisplayTopAnimeInfo
import com.lambao.mrbeast.domain.usecase.GetTopAnimeUseCase
import com.lambao.mrbeast.presentation.ui.common.view_model.empty_data.EmptyDataDelegate
import com.lambao.mrbeast.presentation.ui.common.view_model.empty_data.EmptyDataViewModel
import com.lambao.mrbeast.presentation.ui.common.view_model.query_params.QueryParamsDelegate
import com.lambao.mrbeast.presentation.ui.common.view_model.query_params.QueryParamsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopAnimePagingViewModel @Inject constructor(
    private val getTopAnimeUseCase: GetTopAnimeUseCase,
    dispatcherProvider: DispatcherProvider
) : RemotePagingViewModel<DisplayTopAnimeInfo>(dispatcherProvider), TopAnimePagingDelegate,
    QueryParamsDelegate by QueryParamsViewModel(dispatcherProvider),
    EmptyDataDelegate by EmptyDataViewModel(dispatcherProvider) {

    override fun getTopAnimeList() = items

    override fun fetchTopAnime() {
        fetchData()
    }

    override fun fetchData() {
        handleDataPaging(
            getTopAnimeUseCase.invoke(
                TopParams(
                    type = getType().value,
                    filter = getFilter().value,
                    rating = getRating().value,
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