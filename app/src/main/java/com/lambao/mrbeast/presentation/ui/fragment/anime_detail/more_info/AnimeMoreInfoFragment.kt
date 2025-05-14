package com.lambao.mrbeast.presentation.ui.fragment.anime_detail.more_info

import android.os.Bundle
import com.lambao.base.extension.getParcelableCompat
import com.lambao.base.extension.observeLatest
import com.lambao.base.presentation.ui.fragment.BaseVMFragment
import com.lambao.base.presentation.ui.view.recycler_view.spacing
import com.lambao.mrbeast.domain.model.PairText
import com.lambao.mrbeast.presentation.common.VerticalPairTextAdapter
import com.lambao.mrbeast.utils.Constants
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.FragmentAnimeMoreInfoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnimeMoreInfoFragment :
    BaseVMFragment<FragmentAnimeMoreInfoBinding, AnimeMoreInfoViewModel>() {

    companion object {
        fun newInstance(data: AnimeMoreInfoArgument): AnimeMoreInfoFragment {
            val args = Bundle().apply {
                putParcelable(Constants.Bundle.ARG, data)
            }

            val fragment = AnimeMoreInfoFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val argData by lazy {
        arguments?.getParcelableCompat<AnimeMoreInfoArgument>(Constants.Bundle.ARG)
    }

    private val gridInfoAdapter by lazy {
        VerticalPairTextAdapter<PairText>()
    }

    override fun getLayoutResId() = R.layout.fragment_anime_more_info

    override fun getViewModelClass() = AnimeMoreInfoViewModel::class.java

    override fun onViewReady(savedInstanceState: Bundle?) {
        binding.rvInfo.adapter = gridInfoAdapter
        binding.rvInfo.spacing {
            start = 8
            end = 8
            top = 64
            bottom = 64
        }
    }

    override fun initObserve() {
        binding.viewModel = viewModel

        observeLatest(viewModel.moreInfos) {
            gridInfoAdapter.submitList(it)
        }

        argData?.anime?.let { viewModel.setAnime(it) }
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }
}