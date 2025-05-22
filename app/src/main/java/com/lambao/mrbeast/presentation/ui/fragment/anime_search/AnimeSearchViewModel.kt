package com.lambao.mrbeast.presentation.ui.fragment.anime_search

import android.content.Context
import androidx.paging.map
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.mrbeast.domain.model.Selectable
import com.lambao.mrbeast.domain.model.display.DisplayAnimeFullInfo
import com.lambao.mrbeast.domain.model.display.filter.AnimeFilter
import com.lambao.mrbeast.domain.model.display.selector.OrderBySelector
import com.lambao.mrbeast.domain.model.display.selector.RatingSelector
import com.lambao.mrbeast.domain.model.display.selector.Selector
import com.lambao.mrbeast.domain.model.display.selector.SortSelector
import com.lambao.mrbeast.domain.model.display.selector.StatusSelector
import com.lambao.mrbeast.domain.model.display.selector.TypeSelector
import com.lambao.mrbeast.domain.model.display.selector.UnapprovedSelector
import com.lambao.mrbeast.domain.usecase.anime.GetAnimeSearchPagingUseCase
import com.lambao.mrbeast.presentation.ui.common.view_model.anime_filter.AnimeFilterSelectorDelegate
import com.lambao.mrbeast.presentation.ui.common.view_model.anime_filter.AnimeFilterSelectorViewModel
import com.lambao.mrbeast.presentation.ui.fragment.base.search.SearchViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class AnimeSearchViewModel @Inject constructor(
    private val getAnimeSearchPagingUseCase: GetAnimeSearchPagingUseCase,
    @ApplicationContext context: Context,
    dispatcherProvider: DispatcherProvider
) : SearchViewModel<DisplayAnimeFullInfo>(dispatcherProvider),
    AnimeFilterSelectorDelegate by AnimeFilterSelectorViewModel(context, dispatcherProvider) {

    private val _selectedAnimeFilter = MutableStateFlow<Selectable<AnimeFilter>?>(null)
    val selectedAnimeFilter = _selectedAnimeFilter.asStateFlow()

    fun setSelectedAnimeFilter(item: Selectable<AnimeFilter>?) {
        _selectedAnimeFilter.value = item
    }

    fun fetchSearchBySelector(selector: Selector) {
        when (selector) {
            is UnapprovedSelector ->
                updateSearchParams(
                    getSearchParams().value.copy(
                        unApproved = selector.type.param
                    )
                )

            is TypeSelector -> updateSearchParams(
                getSearchParams().value.copy(
                    type = selector.type.param
                )
            )

            is StatusSelector -> updateSearchParams(
                getSearchParams().value.copy(
                    status = selector.type.param
                )
            )

            is RatingSelector -> updateSearchParams(
                getSearchParams().value.copy(
                    rating = selector.type.param
                )
            )

            is SortSelector -> updateSearchParams(
                getSearchParams().value.copy(
                    sort = selector.type.param
                )
            )

            is OrderBySelector -> updateSearchParams(
                getSearchParams().value.copy(
                    orderBy = selector.type.param
                )
            )
        }
        invalidatePagingData()
        triggerRefreshPage()
    }

    fun fetchSearch() {
        updateSearchParams(
            getSearchParams().value.copy(
                letter = getSearchValue().value
            )
        )
        invalidatePagingData()
        triggerRefreshPage()
    }

    fun getAnimeSearchPaginated(searchValue: String) = getPagingData {
        getAnimeSearchPagingUseCase.invoke(
            getSearchParams().value.copy(letter = searchValue)
        ).catch { setErrorScreenState(it) }
            .map {
                it.map { item -> item as DisplayAnimeFullInfo }
            }
    }

    override fun clearSearchValue() {
        if (getSearchValue().value.isEmpty()) return
        super.clearSearchValue()
        fetchSearch()
    }
}