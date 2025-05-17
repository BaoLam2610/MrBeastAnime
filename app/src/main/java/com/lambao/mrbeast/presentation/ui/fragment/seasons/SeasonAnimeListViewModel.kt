package com.lambao.mrbeast.presentation.ui.fragment.seasons

import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.mrbeast.data.remote.params.seasons.SeasonsParams
import com.lambao.mrbeast.domain.model.display.DisplaySeasonAnimeInfo
import com.lambao.mrbeast.domain.model.type.SeasonType
import com.lambao.mrbeast.domain.usecase.seasons.GetSeasonNowUseCase
import com.lambao.mrbeast.domain.usecase.seasons.GetSeasonUpcomingUseCase
import com.lambao.mrbeast.presentation.ui.fragment.base.anime_list.AnimeListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SeasonAnimeListViewModel @Inject constructor(
    private val getSeasonNowUseCase: GetSeasonNowUseCase,
    private val getSeasonUpcomingUseCase: GetSeasonUpcomingUseCase,
    dispatcherProvider: DispatcherProvider
) : AnimeListViewModel<DisplaySeasonAnimeInfo>(dispatcherProvider) {

    private val _seasonsType = MutableStateFlow(SeasonType.NOW)

    fun setSeasonsType(type: SeasonType?) {
        if (type != null) {
            _seasonsType.value = type
        }
    }

    override fun fetchData() {
        val params = SeasonsParams(
            filter = getFilter().value,
            unapproved = getUnApproved().value,
            continuing = getContinuing().value,
            sfw = getSfw().value,
            page = currentPage.value,
            limit = pageSize.value
        )
        val flowUseCase = when (_seasonsType.value) {
            SeasonType.UPCOMING -> getSeasonUpcomingUseCase.invoke(params)
            else -> getSeasonNowUseCase.invoke(params)
        }
        handleDataPaging(
            flowUseCase,
            onPaging = ::setPaging,
        ) {
            appendItems(it)
            setShowEmptyData(items.value.isEmpty())
        }
    }
}