package com.lambao.mrbeast.presentation.ui.fragment.anime_detail

import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.lambao.base.extension.click
import com.lambao.base.extension.launchWhenCreated
import com.lambao.base.extension.observeLatest
import com.lambao.base.extension.popBackStack
import com.lambao.base.presentation.ui.fragment.BaseVMFragment
import com.lambao.base.presentation.ui.view.view_pager.ViewPagerAdapter
import com.lambao.mrbeast.utils.Constants
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.FragmentAnimeDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class AnimeDetailFragment : BaseVMFragment<FragmentAnimeDetailBinding, AnimeDetailViewModel>() {

    private lateinit var viewPagerAdapter: ViewPagerAdapter

    private val argId by lazy {
        arguments?.getString(Constants.Bundle.ID) ?: ""
    }

    private val animePicturesAdapter by lazy {
        AnimePicturesAdapter()
    }

    override fun getLayoutResId() = R.layout.fragment_anime_detail

    override fun getViewModelClass() = AnimeDetailViewModel::class.java

    override fun onViewReady(savedInstanceState: Bundle?) {
        binding.btnBack.click {
            popBackStack()
        }
        binding.tvSynopsisDesc.click {
            binding.tvSynopsisDesc.toggle()
        }
        setupViewPager()
        setupSliderViewPager()
    }

    override fun initObserve() {
        binding.viewModel = viewModel

        observeLatest(viewModel.anime) {}

        observeLatest(viewModel.animePictures) {
            animePicturesAdapter.submitList(it)
        }

        observeLatest(viewModel.shouldShowFullInfo) {}

        observeLatest(viewModel.screenTypes) {
            if (it.isEmpty()) return@observeLatest
            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                tab.text = it[position].title
            }.attach()
        }

        observeLatest(viewModel.fragments) {
            viewPagerAdapter.submitList(it)
        }

        viewModel.fetchAnimeInfo(argId)
        launchWhenCreated {
            delay(3000)
            viewModel.fetchAnimePictures(argId)
        }
    }

    private fun setupViewPager() {
        viewPagerAdapter = ViewPagerAdapter(
            childFragmentManager,
            lifecycle
        )
        binding.viewPager.adapter = viewPagerAdapter
        binding.viewPager.isUserInputEnabled = false
    }

    private fun setupSliderViewPager() {
        binding.sliderViewPager.adapter = animePicturesAdapter
    }
}