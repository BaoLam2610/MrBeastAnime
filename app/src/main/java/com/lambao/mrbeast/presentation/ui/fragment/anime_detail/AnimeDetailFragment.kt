package com.lambao.mrbeast.presentation.ui.fragment.anime_detail

import android.os.Bundle
import androidx.core.os.bundleOf
import com.google.android.material.tabs.TabLayout
import com.lambao.base.extension.click
import com.lambao.base.extension.getParcelableCompat
import com.lambao.base.extension.observeLatest
import com.lambao.base.extension.popBackStack
import com.lambao.base.extension.setOnLoadMoreListener
import com.lambao.base.extension.tryNavigate
import com.lambao.base.presentation.ui.dialog.BaseVMDialog
import com.lambao.base.presentation.ui.fragment.paging.BaseManualPagingFragment
import com.lambao.mrbeast.presentation.ui.fragment.anime_rating.AnimeRatingArgument
import com.lambao.mrbeast.utils.Constants
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.FragmentAnimeDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnimeDetailFragment : BaseVMDialog<FragmentAnimeDetailBinding, AnimeDetailViewModel>(),
    TabLayout.OnTabSelectedListener {

    private val argData by lazy {
        arguments?.getParcelableCompat<AnimeDetailArgument>(Constants.Bundle.ARG)
    }

    override fun getLayoutResId() = R.layout.fragment_anime_detail

    override fun getViewModelClass() = AnimeDetailViewModel::class.java

    override fun getTheme() = com.lambao.base.R.style.full_screen_dialog

    override fun onViewReady(savedInstanceState: Bundle?) {
        dialog?.window?.attributes?.windowAnimations = com.lambao.base.R.style.dialog_animation
        binding.btnBack.click {
            popBackStack()
        }
        binding.tvSynopsisDesc.click {
            binding.tvSynopsisDesc.toggle()
        }
        binding.scoreView.root.click {
            tryNavigate(
                R.id.action_animeDetailFragment_to_animeRatingFragment,
                args = bundleOf(
                    Constants.Bundle.ARG to AnimeRatingArgument(
                        id = viewModel.anime.value?.getId(),
                        title = viewModel.anime.value?.displayTitle()
                    )
                )
            )
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

        viewModel.setSourceFragmentId(argData?.sourceFragmentId)
        viewModel.fetchAnimeInfo(argData?.id ?: "")
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
        binding.nestedScrollView.setOnLoadMoreListener {
            val currentFragment = childFragmentManager.fragments.find { it.isVisible }
            if (currentFragment is BaseManualPagingFragment<*, *>) {
                currentFragment.tryLoadMore()
            }
        }
    }
}