package com.lambao.mrbeast.presentation.ui.fragment.tops

import android.os.Bundle
import com.lambao.base.extension.observeLatest
import com.lambao.base.extension.setOnLoadMoreListener
import com.lambao.base.presentation.ui.view.recycler_view.spacing
import com.lambao.mrbeast.domain.model.display.DisplayTopAnimeInfo
import com.lambao.mrbeast.presentation.ui.common.navigator.NavigatorDelegate
import com.lambao.mrbeast.presentation.ui.common.navigator.NavigatorDelegateImpl
import com.lambao.mrbeast.presentation.ui.fragment.base.anime_list.AnimeListFragment
import com.lambao.mrbeast.utils.Constants
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.FragmentTopAnimeListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopAnimeListFragment :
    AnimeListFragment<DisplayTopAnimeInfo, FragmentTopAnimeListBinding, TopAnimeListViewModel>() {

    private val argTitle by lazy {
        arguments?.getString(Constants.Bundle.TITLE) ?: ""
    }

    private val argType by lazy {
        arguments?.getString(Constants.Bundle.TYPE) ?: ""
    }

    private val argFilter by lazy {
        arguments?.getString(Constants.Bundle.FILTER) ?: ""
    }

    private val argRating by lazy {
        arguments?.getString(Constants.Bundle.RATING) ?: ""
    }

    private val navigator: NavigatorDelegate by lazy {
        NavigatorDelegateImpl(this)
    }

    private val topAnimeAdapter by lazy {
        TopAnimeListAdapter { item, position ->
            navigator.navigateTopAnimeToDetail(item)
        }
    }

    override fun getChildLayoutResId() = R.layout.fragment_top_anime_list

    override fun getViewModelClass() = TopAnimeListViewModel::class.java

    override fun getTitleScreen() = argTitle

    override fun onChildViewReady(savedInstanceState: Bundle?) {
        childBinding.rvData.adapter = topAnimeAdapter
        childBinding.rvData.spacing {
            top = 8
            bottom = 8
            start = 8
            end = 8
        }
        setupNestedScrollListener()
    }

    override fun initObserve() {
        childBinding.viewModel = viewModel

        observeLatest(viewModel.items) {
            topAnimeAdapter.submitList(it)
        }

        with(viewModel) {
            setType(argType)
            setFilter(argFilter)
            setRating(argRating)
            fetchData()
        }
    }

    private fun setupNestedScrollListener() {
        binding.nestedScrollView.setOnLoadMoreListener {
            if (viewModel.hasMoreItems()) {
                viewModel.loadMoreItems()
            }
        }
    }
}