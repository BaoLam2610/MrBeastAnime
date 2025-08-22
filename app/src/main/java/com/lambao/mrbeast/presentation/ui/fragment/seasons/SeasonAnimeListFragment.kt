package com.lambao.mrbeast.presentation.ui.fragment.seasons

import android.os.Bundle
import com.lambao.presentation.extension.getParcelableCompat
import com.lambao.presentation.extension.observeLatest
import com.lambao.base.presentation.ui.recycler_view.paging.DefaultLoadStateAdapter
import com.lambao.base.presentation.ui.view.recycler_view.setupGridLayoutManagerWithFooterSpan
import com.lambao.base.presentation.ui.view.recycler_view.spacing
import com.lambao.mrbeast.domain.model.display.DisplaySeasonAnimeInfo
import com.lambao.mrbeast.presentation.ui.common.navigator.NavigatorDelegate
import com.lambao.mrbeast.presentation.ui.common.navigator.NavigatorDelegateImpl
import com.lambao.mrbeast.presentation.ui.fragment.base.anime_list.AnimeListFragment
import com.lambao.mrbeast.utils.Constants
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.FragmentSeasonAnimeListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeasonAnimeListFragment :
    AnimeListFragment<DisplaySeasonAnimeInfo, FragmentSeasonAnimeListBinding, SeasonAnimeListViewModel>() {

    override val argData by lazy {
        arguments?.getParcelableCompat<SeasonAnimeListArgument>(Constants.Bundle.ARG)
    }

    private val navigator: NavigatorDelegate by lazy {
        NavigatorDelegateImpl(this)
    }

    private val seasonAnimeAdapter by lazy {
        SeasonAnimeListAdapter { item, _ ->
            navigator.navigateSeasonAnimeToDetail(item)
        }
    }

    private val loadStateAdapter by lazy {
        DefaultLoadStateAdapter()
    }

    override fun getChildLayoutResId() = R.layout.fragment_season_anime_list

    override fun getViewModelClass() = SeasonAnimeListViewModel::class.java

    override fun getTitleScreen() = argData?.title ?: ""

    override fun onChildViewReady(savedInstanceState: Bundle?) {
        childBinding.rvData.adapter = seasonAnimeAdapter.withLoadStateFooter(loadStateAdapter)
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
    }

    override fun initObserve() {
        childBinding.viewModel = viewModel
        with(viewModel) {
            setFilter(argData?.filter ?: "")
            setUnApproved(argData?.unApproved)
            setContinuing(argData?.continuing)
            setSfw(argData?.sfw)
            setSeasonsType(argData?.seasonType)
            if (shouldLoadData().value) {
                setLoadData(false)
            }
        }

        observeLatest(viewModel.getSeasonAnime()) {
            seasonAnimeAdapter.submitData(it)
        }

        seasonAnimeAdapter.addLoadStateListener { loadState ->
            viewModel.handleLoadStates(loadState, seasonAnimeAdapter.itemCount)
        }
    }
}