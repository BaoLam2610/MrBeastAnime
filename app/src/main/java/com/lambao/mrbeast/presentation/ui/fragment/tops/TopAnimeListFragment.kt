package com.lambao.mrbeast.presentation.ui.fragment.tops

import android.os.Bundle
import com.lambao.base.extension.observeLatest
import com.lambao.base.presentation.ui.recycler_view.paging.DefaultLoadStateAdapter
import com.lambao.base.presentation.ui.view.recycler_view.setupGridLayoutManagerWithFooterSpan
import com.lambao.base.presentation.ui.view.recycler_view.spacing
import com.lambao.mrbeast.domain.model.display.DisplayTopAnimeInfo
import com.lambao.mrbeast.presentation.ui.common.navigator.NavigatorDelegate
import com.lambao.mrbeast.presentation.ui.common.navigator.NavigatorDelegateImpl
import com.lambao.mrbeast.presentation.ui.fragment.base.anime_list.AnimeListFragment
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.FragmentTopAnimeListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopAnimeListFragment :
    AnimeListFragment<DisplayTopAnimeInfo, FragmentTopAnimeListBinding, TopAnimeListViewModel>() {

    private val navigator: NavigatorDelegate by lazy {
        NavigatorDelegateImpl(this)
    }

    private val topAnimeAdapter by lazy {
        TopAnimeListAdapter { item, _ ->
            navigator.navigateTopAnimeToDetail(item)
        }
    }

    private val loadStateAdapter by lazy {
        DefaultLoadStateAdapter {
            topAnimeAdapter.retry()
        }
    }

    override fun getChildLayoutResId() = R.layout.fragment_top_anime_list

    override fun getViewModelClass() = TopAnimeListViewModel::class.java

    override fun getTitleScreen() = argData?.title ?: ""

    override fun onChildViewReady(savedInstanceState: Bundle?) {
        childBinding.rvData.adapter = topAnimeAdapter.withLoadStateFooter(loadStateAdapter)
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
            setType(argData?.type ?: "")
            setFilter(argData?.filter ?: "")
            setRating(argData?.rating ?: "")
            setSfw(argData?.sfw)
            if (shouldLoadData().value) {
                setLoadData(false)
            }
        }

        observeLatest(viewModel.getTopAnimePaginated()) {
            topAnimeAdapter.submitData(it)
        }

        topAnimeAdapter.addLoadStateListener { loadState ->
            viewModel.handleLoadStates(loadState, topAnimeAdapter.itemCount)
        }
    }
}