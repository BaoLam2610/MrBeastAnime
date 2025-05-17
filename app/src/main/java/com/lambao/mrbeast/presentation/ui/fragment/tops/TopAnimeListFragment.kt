package com.lambao.mrbeast.presentation.ui.fragment.tops

import android.os.Bundle
import com.lambao.base.extension.launchWhenCreated
import com.lambao.base.extension.observeLatest
import com.lambao.base.presentation.ui.view.recycler_view.spacing
import com.lambao.mrbeast.presentation.ui.common.navigator.NavigatorDetail
import com.lambao.mrbeast.presentation.ui.common.navigator.NavigatorDetailImpl
import com.lambao.mrbeast.presentation.ui.fragment.base.anime_list.AnimeListFragment
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.FragmentTopAnimeListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopAnimeListFragment :
    AnimeListFragment<FragmentTopAnimeListBinding, TopAnimeListViewModel>() {

    private val navigator: NavigatorDetail by lazy {
        NavigatorDetailImpl(this)
    }

    private val topAnimeAdapter by lazy {
        TopAnimeListAdapter { item, position ->
            navigator.navigateTopAnimeToDetail(item)
        }
    }

    override fun getChildLayoutResId() = R.layout.fragment_top_anime_list

    override fun getViewModelClass() = TopAnimeListViewModel::class.java

    override fun getTitleScreen() = getString(R.string.anime_movie)

    override fun onChildViewReady(savedInstanceState: Bundle?) {
        childBinding.rvData.adapter = topAnimeAdapter
        childBinding.rvData.spacing {
            top = 8
            bottom = 8
            start = 8
            end = 8
        }
    }

    override fun initObserve() {
        childBinding.viewModel = viewModel

        observeLatest(viewModel.getTopAnimeList()) {
            topAnimeAdapter.submitList(it)
        }
        launchWhenCreated {
            viewModel.fetchTopAnime()
        }
    }
}