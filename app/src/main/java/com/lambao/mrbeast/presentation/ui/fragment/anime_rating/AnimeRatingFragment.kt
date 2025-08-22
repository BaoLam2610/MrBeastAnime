package com.lambao.mrbeast.presentation.ui.fragment.anime_rating

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import com.lambao.presentation.extension.click
import com.lambao.presentation.extension.getParcelableCompat
import com.lambao.presentation.extension.observeLatest
import com.lambao.presentation.extension.popBackStack
import com.lambao.base.presentation.ui.dialog.BaseVMDialog
import com.lambao.mrbeast.utils.Constants
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.FragmentAnimeRatingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnimeRatingFragment : BaseVMDialog<FragmentAnimeRatingBinding, AnimeRatingViewModel>(),
    TabLayout.OnTabSelectedListener {

    private val argData by lazy {
        arguments?.getParcelableCompat<AnimeRatingArgument>(Constants.Bundle.ARG)
    }

    override fun getLayoutResId() = R.layout.fragment_anime_rating

    override fun getTheme() = com.lambao.base.R.style.full_screen_dialog

    override fun getViewModelClass() = AnimeRatingViewModel::class.java

    override fun onViewReady(savedInstanceState: Bundle?) {
        binding.btnBack.click {
            popBackStack()
        }
        binding.tvTitle.text = argData?.title
    }

    override fun initObserve() {
        binding.viewModel = viewModel
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

        viewModel.setAnimeId(argData?.id ?: "")
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
}