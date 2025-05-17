package com.lambao.mrbeast.presentation.ui.fragment.home

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.lambao.base.extension.launchWhenCreated
import com.lambao.base.extension.navigate
import com.lambao.base.extension.observeLatest
import com.lambao.base.extension.showToast
import com.lambao.base.presentation.ui.fragment.BaseVMFragment
import com.lambao.base.presentation.ui.view.recycler_view.spacing
import com.lambao.mrbeast.domain.model.type.HomeType
import com.lambao.mrbeast.domain.model.type.InfoType
import com.lambao.mrbeast.presentation.ui.common.adapter.anime_info.AnimeContainerAdapter
import com.lambao.mrbeast.presentation.ui.common.navigator.NavigatorDetail
import com.lambao.mrbeast.presentation.ui.common.navigator.NavigatorDetailImpl
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class HomeFragment : BaseVMFragment<FragmentHomeBinding, HomeViewModel>() {

    private lateinit var topAnimeSliderAdapter: TopAnimeSliderAdapter

    private val navigator: NavigatorDetail by lazy {
        NavigatorDetailImpl(this)
    }

    private val animeContainerAdapter by lazy {
        AnimeContainerAdapter(
            onSeeMoreClickListener = ::handleSeeMoreClickListener,
            onItemClickListener = { item, _ ->
                navigator.navigateHomeToDetail(item)
            }
        )
    }

    override fun getLayoutResId() = R.layout.fragment_home

    override fun getViewModelClass() = HomeViewModel::class.java

    override fun onViewReady(savedInstanceState: Bundle?) {
        binding.rvInfo.adapter = animeContainerAdapter
        binding.rvInfo.spacing {
            top = 32
            bottom = 16
        }
        setupViewPager()
    }

    override fun initObserve() {
        binding.viewModel = viewModel

        observeLatest(viewModel.getTopAnimeList()) {
            topAnimeSliderAdapter.submitList(it)
        }

        observeLatest(viewModel.animeDisplayList) {
            animeContainerAdapter.submitList(it)
        }

        if (viewModel.shouldLoadDataValue) {
            viewModel.fetchAnimeData()
            launchWhenCreated {
                delay(3000)
                viewModel.fetchSeasonUpcoming()
            }
            viewModel.setLoadData(false)
        }
    }

    private fun setupViewPager() {
        topAnimeSliderAdapter = TopAnimeSliderAdapter { item, _ ->
            navigator.navigateHomeToDetail(item)
        }
        binding.viewPager.apply {
            adapter = topAnimeSliderAdapter
            clipChildren = false
            clipToPadding = false
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            /*post {
                layoutParams = layoutParams.apply {
                    height = (resources.displayMetrics.heightPixels * 0.28).toInt()
                }
            }*/
        }
    }

    private fun handleSeeMoreClickListener(type: InfoType) {
        if (type is HomeType) {
            when (type) {
                HomeType.TopAnime -> {
                    navigate(R.id.action_homeFragment_to_topAnimeListFragment)
                }

                HomeType.TvSeasonNow -> {
                    navigate(R.id.action_homeFragment_to_topAnimeListFragment)
                }

                HomeType.MovieSeasonNow -> showToast(type.toString())
                HomeType.SeasonUpcoming -> showToast(type.toString())
            }
        }
    }
}