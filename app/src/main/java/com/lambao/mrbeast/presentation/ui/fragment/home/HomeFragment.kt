package com.lambao.mrbeast.presentation.ui.fragment.home

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.lambao.base.extension.observeLatest
import com.lambao.base.presentation.ui.fragment.BaseVMFragment
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseVMFragment<FragmentHomeBinding, HomeViewModel>() {

    lateinit var topAnimeSliderAdapter: TopAnimeSliderAdapter

    override fun getLayoutResId() = R.layout.fragment_home

    override fun getViewModelClass() = HomeViewModel::class.java

    override fun onViewReady(savedInstanceState: Bundle?) {
        binding.viewModel = viewModel
        setupViewPager()
    }

    override fun initObserve() {
        observeLatest(viewModel.topAnimeSliders) {
            topAnimeSliderAdapter.submitList(it)
        }

        viewModel.getTopAnimeSliders()
    }

    private fun setupViewPager() {
        topAnimeSliderAdapter = TopAnimeSliderAdapter()
        binding.viewPager.apply {
            adapter = topAnimeSliderAdapter
            clipChildren = false
            clipToPadding = false
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }
    }
}