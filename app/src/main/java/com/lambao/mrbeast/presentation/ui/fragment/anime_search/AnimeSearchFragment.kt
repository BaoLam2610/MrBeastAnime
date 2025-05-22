package com.lambao.mrbeast.presentation.ui.fragment.anime_search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.core.os.bundleOf
import androidx.paging.PagingData
import com.lambao.base.extension.click
import com.lambao.base.extension.hideKeyboard
import com.lambao.base.extension.launchWhen
import com.lambao.base.extension.launchWhenCreated
import com.lambao.base.extension.navigateForResult
import com.lambao.base.extension.observeLatest
import com.lambao.base.presentation.ui.recycler_view.paging.DefaultLoadStateAdapter
import com.lambao.base.presentation.ui.view.recycler_view.setupGridLayoutManagerWithFooterSpan
import com.lambao.base.presentation.ui.view.recycler_view.spacing
import com.lambao.mrbeast.domain.model.display.DisplayAnimeFullInfo
import com.lambao.mrbeast.domain.model.display.selector.Selector
import com.lambao.mrbeast.presentation.ui.common.navigator.NavigatorDelegate
import com.lambao.mrbeast.presentation.ui.common.navigator.NavigatorDelegateImpl
import com.lambao.mrbeast.presentation.ui.fragment.anime_filter.AnimeFilterArgument
import com.lambao.mrbeast.presentation.ui.fragment.base.search.SearchFragment
import com.lambao.mrbeast.utils.Constants
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.FragmentAnimeSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class AnimeSearchFragment :
    SearchFragment<DisplayAnimeFullInfo, FragmentAnimeSearchBinding, AnimeSearchViewModel>() {

    private val navigator: NavigatorDelegate by lazy {
        NavigatorDelegateImpl(this)
    }

    private val animeFilterAdapter by lazy {
        AnimeFilterAdapter { item, _ ->
            if (item.data.selectors.isEmpty()) {
                if (!viewModel.isSameDefaultFilter()) {
                    viewModel.resetFilter()
                    viewModel.resetParams()
                    viewModel.fetchSearch()
                    launchWhen {
                        delay(300)
                        childBinding.rvFilter.scrollToPosition(0)
                    }
                }
                return@AnimeFilterAdapter
            }
            viewModel.setSelectedAnimeFilter(item)
            navigateForResult<Selector>(
                R.id.action_animeSearchFragment_to_animeFilterBottomSheet,
                bundleOf(
                    Constants.Bundle.ARG to AnimeFilterArgument(
                        title = item.data.displayText,
                        filters = item.data.selectors,
                        filterSelected = item.data.selector
                    )
                ),
                resultKey = Constants.Bundle.RESULT
            ) {
                viewModel.selectedAnimeFilter.value?.let { selected ->
                    clearDataAdapter()
                    viewModel.onUpdateAnimeFilterSelector(selected, it)
                    viewModel.fetchSearchBySelector(it)
                    viewModel.setSelectedAnimeFilter(null)
                }
            }
        }
    }

    private val animeSearchAdapter by lazy {
        AnimeSearchAdapter { item, _ ->
            navigator.navigateSearchToDetail(item)
        }
    }

    private val loadStateAdapter by lazy {
        DefaultLoadStateAdapter {
            animeSearchAdapter.retry()
        }
    }

    override fun getChildLayoutResId() = R.layout.fragment_anime_search

    override fun getViewModelClass() = AnimeSearchViewModel::class.java

    override fun onChildViewReady(savedInstanceState: Bundle?) {
        childBinding.rvFilter.adapter = animeFilterAdapter
        childBinding.rvFilter.spacing {
            top = 8
            bottom = 8
            start = 8
            end = 8
        }
        childBinding.rvData.adapter = animeSearchAdapter.withLoadStateFooter(loadStateAdapter)
        childBinding.rvData.spacing {
            top = 8
            bottom = 8
            start = 8
            end = 8
        }
        childBinding.rvData.setupGridLayoutManagerWithFooterSpan(
            requireContext(),
            2
        )
        childBinding.btnFilter.click {

        }
        binding.edtSearch.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                requireContext().hideKeyboard(v)
                clearDataAdapter()
                viewModel.fetchSearch()
            }
            false
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun initObserve() {
        super.initObserve()
        childBinding.viewModel = viewModel
        with(viewModel) {
            setUnApproved(argData?.unApproved)
            setQ(argData?.q ?: "")
            setType(argData?.type ?: "")
            setScore(argData?.score)
            setMinScore(argData?.minScore)
            setMaxScore(argData?.maxScore)
            setStatus(argData?.status ?: "")
            setRating(argData?.rating ?: "")
            setSfw(argData?.sfw)
            setGenres(argData?.genres ?: emptyList())
            setGenresExclude(argData?.explicitGenres ?: emptyList())
            setOrderBy(argData?.orderBy ?: "")
            setSort(argData?.sort ?: "")
            setLetter(argData?.letter ?: "")
            setProducers(argData?.producers ?: emptyList())
            setStartDate(argData?.startDate ?: "")
            setEndDate(argData?.endDate ?: "")
            updateSearchParams(
                getSearchParams().value.copy(
                    unApproved = getUnApproved().value,
                    q = getQ().value,
                    type = getType().value,
                    score = getScore().value,
                    minScore = getMinScore().value,
                    maxScore = getMaxScore().value,
                    status = getStatus().value,
                    rating = getRating().value,
                    sfw = getSfw().value,
                    genres = getGenres().value,
                    genresExclude = getGenresExclude().value,
                    orderBy = getOrderBy().value,
                    sort = getSort().value,
                    letter = getSearchValue().value,
                    producers = getProducers().value,
                    startDate = getStartDate().value,
                    endDate = getEndDate().value,
                    page = 1,
                    limit = 20
                )
            )
            setSearchValue(argData?.letter ?: "")
            if (shouldLoadData().value) {
                setLoadData(false)
            }
        }

        observeLatest(viewModel.shouldNotifyAnimeFilter()) {
            animeFilterAdapter.notifyDataSetChanged()
        }

        observeLatest(viewModel.getSelectableAnimeFilter()) {
            animeFilterAdapter.submitList(it)
        }

        observeLatest(viewModel.shouldRefreshPage) {
            viewModel.getAnimeSearchPaginated(viewModel.getSearchValue().value)
                .collectLatest { pagingData ->
                    animeSearchAdapter.submitData(pagingData)
                }
        }

        animeSearchAdapter.addLoadStateListener { loadState ->
            viewModel.handleLoadStates(loadState, animeSearchAdapter.itemCount)
        }

        viewModel.fetchSearch()
    }

    private fun clearDataAdapter() {
        launchWhenCreated {
            animeSearchAdapter.submitData(PagingData.empty())
        }
    }
}