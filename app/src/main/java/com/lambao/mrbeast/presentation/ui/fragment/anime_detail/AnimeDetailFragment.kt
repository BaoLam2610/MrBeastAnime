package com.lambao.mrbeast.presentation.ui.fragment.anime_detail

import android.os.Bundle
import androidx.core.widget.NestedScrollView
import com.google.android.material.tabs.TabLayout
import com.lambao.base.extension.click
import com.lambao.base.extension.observeLatest
import com.lambao.base.extension.popBackStack
import com.lambao.base.presentation.ui.fragment.BaseVMFragment
import com.lambao.base.presentation.ui.fragment.paging.BasePagingFragment
import com.lambao.mrbeast.utils.Constants
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.FragmentAnimeDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnimeDetailFragment : BaseVMFragment<FragmentAnimeDetailBinding, AnimeDetailViewModel>(),
    TabLayout.OnTabSelectedListener {

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
        setupNestedScrollListener()
    }

    override fun initObserve() {
        binding.viewModel = viewModel

        observeLatest(viewModel.anime)

        observeLatest(viewModel.shouldShowFullInfo)

        observeLatest(viewModel.screenTypes) {
            binding.tabLayout.removeAllTabs()
            it.forEach { screenType ->
                binding.tabLayout.addTab(binding.tabLayout.newTab().setText(screenType.title))
            }
        }

        observeLatest(viewModel.fragments) {
            if (it.isEmpty()) return@observeLatest
            childFragmentManager.beginTransaction().apply {
                childFragmentManager.fragments.forEach { fragment ->
                    if (fragment.id == R.id.frameLayout) {
                        remove(fragment)
                    }
                }
                add(R.id.frameLayout, it.first(), it.first()::class.java.name)
                commit()
            }
            binding.tabLayout.removeOnTabSelectedListener(this)
            binding.tabLayout.addOnTabSelectedListener(this)
        }

        viewModel.fetchAnimeInfo(argId)
    }

    override fun onTabSelected(tab: TabLayout.Tab) {
        val fragment = viewModel.fragments.value[tab.position]
        val existingFragment = childFragmentManager.findFragmentByTag(fragment::class.java.name)
        childFragmentManager.beginTransaction().apply {
            childFragmentManager.fragments.forEach { fragment ->
                if (fragment.isVisible) hide(fragment)
            }

            if (existingFragment != null) {
                show(existingFragment)
            } else {
                add(R.id.frameLayout, fragment, fragment::class.java.name)
            }
            commit()
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) = Unit

    override fun onTabReselected(tab: TabLayout.Tab?) = Unit

    private fun setupNestedScrollListener() {
        binding.nestedScrollView.setOnScrollChangeListener { v: NestedScrollView, _: Int, scrollY: Int, _: Int, oldScrollY: Int ->
            if (v.getChildAt(v.childCount - 1) != null) {
                if (
                    (scrollY >= (v.getChildAt(v.childCount - 1).measuredHeight - v.measuredHeight)) &&
                    scrollY > oldScrollY
                ) {
                    val currentFragment = childFragmentManager.fragments.find { it.isVisible }
                    if (currentFragment is BasePagingFragment<*, *>) {
                        currentFragment.tryLoadMore()
                    }
                }
            }
        }
    }
}