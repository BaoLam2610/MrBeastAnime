package com.lambao.mrbeast.presentation.ui.fragment.anime_detail

import android.os.Bundle
import com.lambao.base.extension.click
import com.lambao.base.extension.observeLatest
import com.lambao.base.extension.popBackStack
import com.lambao.base.presentation.ui.fragment.BaseVMFragment
import com.lambao.mrbeast.utils.Constants
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.FragmentAnimeDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnimeDetailFragment : BaseVMFragment<FragmentAnimeDetailBinding, AnimeDetailViewModel>() {

    private val argId by lazy {
        arguments?.getString(Constants.Bundle.ID) ?: ""
    }

    override fun getLayoutResId() = R.layout.fragment_anime_detail

    override fun getViewModelClass() = AnimeDetailViewModel::class.java

    override fun onViewReady(savedInstanceState: Bundle?) {
        binding.btnBack.click {
            popBackStack()
        }
    }

    override fun initObserve() {
        binding.viewModel = viewModel

        observeLatest(viewModel.anime) {

        }

        viewModel.fetchAnimeInfo(argId)
    }
}