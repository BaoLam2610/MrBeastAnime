package com.lambao.mrbeast.presentation.ui.fragment.anime_detail

import android.os.Bundle
import androidx.core.widget.NestedScrollView
import com.google.android.material.tabs.TabLayoutMediator
import com.lambao.base.extension.click
import com.lambao.base.extension.observeLatest
import com.lambao.base.extension.popBackStack
import com.lambao.base.presentation.ui.fragment.BaseVMFragment
import com.lambao.base.presentation.ui.fragment.paging.BasePagingFragment
import com.lambao.base.presentation.ui.view.view_pager.ViewPagerAdapter
import com.lambao.mrbeast.utils.Constants
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.FragmentAnimeDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnimeDetailFragment : BaseVMFragment<FragmentAnimeDetailBinding, AnimeDetailViewModel>() {

    private lateinit var viewPagerAdapter: ViewPagerAdapter

    private val argId by lazy {
        arguments?.getString(Constants.Bundle.ID) ?: ""
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
        setupNestedScrollListener()
    }

    override fun initObserve() {
        binding.viewModel = viewModel

        observeLatest(viewModel.anime) {}

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
    }

    private fun setupViewPager() {
        viewPagerAdapter = ViewPagerAdapter(
            childFragmentManager,
            lifecycle
        )
        binding.viewPager.adapter = viewPagerAdapter
        binding.viewPager.isUserInputEnabled = false
    }

    private fun setupNestedScrollListener() {
        binding.nestedScrollView.setOnScrollChangeListener { v: NestedScrollView, _: Int, scrollY: Int, _: Int, oldScrollY: Int ->
            if (v.getChildAt(v.childCount - 1) != null) {
                if (
                    (scrollY >= (v.getChildAt(v.childCount - 1).measuredHeight - v.measuredHeight)) &&
                    scrollY > oldScrollY
                ) {
                    val currentFragment =
                        viewPagerAdapter.getFragment(binding.viewPager.currentItem)
                    if (currentFragment is BasePagingFragment<*, *>) {
                        currentFragment.tryLoadMore()
                    }
                }
            }
        }
    }
}