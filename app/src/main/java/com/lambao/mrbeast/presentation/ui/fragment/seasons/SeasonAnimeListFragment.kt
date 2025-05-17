package com.lambao.mrbeast.presentation.ui.fragment.seasons

import android.os.Bundle
import com.lambao.base.extension.getParcelableCompat
import com.lambao.base.extension.observeLatest
import com.lambao.base.extension.setOnLoadMoreListener
import com.lambao.base.presentation.ui.view.recycler_view.spacing
import com.lambao.mrbeast.domain.model.display.DisplaySeasonAnimeInfo
import com.lambao.mrbeast.domain.model.type.SeasonType
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

    private val argSeasonType by lazy {
        arguments?.getParcelableCompat<SeasonType>(Constants.Bundle.SEASON_TYPE)
    }

    private val argTitle by lazy {
        arguments?.getString(Constants.Bundle.TITLE) ?: ""
    }

    private val argFilter by lazy {
        arguments?.getString(Constants.Bundle.FILTER) ?: ""
    }

    private val argUnApproved by lazy {
        arguments?.getBoolean(Constants.Bundle.UNAPPROVED)
    }

    private val argContinuing by lazy {
        arguments?.getBoolean(Constants.Bundle.CONTINUING)
    }

    private val argSfw by lazy {
        arguments?.getBoolean(Constants.Bundle.SFW)
    }

    private val navigator: NavigatorDelegate by lazy {
        NavigatorDelegateImpl(this)
    }

    private val seasonAnimeAdapter by lazy {
        SeasonAnimeListAdapter { item, position ->
            navigator.navigateSeasonAnimeToDetail(item)
        }
    }

    override fun getChildLayoutResId() = R.layout.fragment_season_anime_list

    override fun getViewModelClass() = SeasonAnimeListViewModel::class.java

    override fun getTitleScreen() = argTitle

    override fun onChildViewReady(savedInstanceState: Bundle?) {
        childBinding.rvData.adapter = seasonAnimeAdapter
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
            seasonAnimeAdapter.submitList(it)
        }

        with(viewModel) {
            setFilter(argFilter)
            setUnApproved(argUnApproved)
            setContinuing(argContinuing)
            setSfw(argSfw)
            setSeasonsType(argSeasonType)
            if (shouldLoadData().value) {
                fetchData()
                setLoadData(false)
            }
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