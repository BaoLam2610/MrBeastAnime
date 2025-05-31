package com.lambao.mrbeast.presentation.ui.fragment.anime_detail.videos_episodes

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
import com.lambao.mrbeast_anime.databinding.FragmentAnimeVideosEpisodesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class AnimeVideosEpisodesFragment :
    BaseManualPagingFragment<FragmentAnimeVideosEpisodesBinding, AnimeVideosEpisodesViewModel>() {

    companion object {
        fun newInstance(data: AnimeVideosEpisodesArgument?): AnimeVideosEpisodesFragment {
            val args = Bundle().apply {
                putParcelable(Constants.Bundle.ARG, data)
            }

            val fragment = AnimeVideosEpisodesFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val argData by lazy {
        arguments?.getParcelableCompat<AnimeVideosEpisodesArgument>(Constants.Bundle.ARG)
    }

    private val episodesAdapter by lazy {
        AnimeVideosEpisodesAdapter { item, _ ->

        }
    }

    override fun getLayoutResId() = R.layout.fragment_anime_videos_episodes

    override fun getViewModelClass() = AnimeVideosEpisodesViewModel::class.java

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

        observeLatest(viewModel.items) {
            episodesAdapter.submitList(it)
        }

        launchWhenCreated {
            delay(1500)
            argData?.let { state ->
                viewModel.fetchAnimeVideosEpisodes(state.id ?: "")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }
}