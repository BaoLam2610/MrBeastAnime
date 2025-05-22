package com.lambao.mrbeast.presentation.ui.fragment.anime_detail.episodes

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.lambao.base.extension.getParcelableCompat
import com.lambao.base.extension.launchWhenCreated
import com.lambao.base.extension.observeLatest
import com.lambao.base.presentation.ui.fragment.paging.BaseManualPagingFragment
import com.lambao.mrbeast.utils.Constants
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.FragmentAnimeEpisodesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnimeEpisodesFragment :
    BaseManualPagingFragment<FragmentAnimeEpisodesBinding, AnimeEpisodesViewModel>() {

    companion object {
        fun newInstance(data: AnimeEpisodesArgument?): AnimeEpisodesFragment {
            val args = Bundle().apply {
                putParcelable(Constants.Bundle.ARG, data)
            }

            val fragment = AnimeEpisodesFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val argData by lazy {
        arguments?.getParcelableCompat<AnimeEpisodesArgument>(Constants.Bundle.ARG)
    }

    private val episodesAdapter by lazy {
        AnimeEpisodesAdapter { item, _ ->

        }
    }

    override fun getLayoutResId() = R.layout.fragment_anime_episodes

    override fun getViewModelClass() = AnimeEpisodesViewModel::class.java

    override fun onViewReady(savedInstanceState: Bundle?) {
        binding.rvEpisodes.adapter = episodesAdapter
        binding.rvEpisodes.addItemDecoration(
            MaterialDividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            ).apply {
                dividerThickness = 1
                dividerColor = ContextCompat.getColor(
                    requireContext(),
                    com.lambao.base.R.color.background_disabled
                )
            }
        )
    }

    override fun initObserve() {
        binding.viewModel = viewModel

        observeLatest(viewModel.episodesWithThumbnails) {
            episodesAdapter.submitList(it)
        }

        launchWhenCreated {
            argData?.let { state ->
                viewModel.fetchAnimeEpisodes(state.id ?: "")
                viewModel.setThumbnail(state.thumbnail ?: "")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }
}