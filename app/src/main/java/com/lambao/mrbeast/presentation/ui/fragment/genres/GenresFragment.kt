package com.lambao.mrbeast.presentation.ui.fragment.genres

import android.os.Bundle
import androidx.core.os.bundleOf
import com.lambao.base.extension.launchWhenCreated
import com.lambao.base.extension.navigate
import com.lambao.base.extension.observeLatest
import com.lambao.base.presentation.ui.fragment.BaseVMFragment
import com.lambao.base.presentation.ui.view.recycler_view.spacing
import com.lambao.mrbeast.domain.model.display.DisplayAnimeInfo
import com.lambao.mrbeast.presentation.ui.common.adapter.anime_info.AnimeContainerAdapter
import com.lambao.mrbeast.utils.Constants
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.FragmentGenresBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class GenresFragment : BaseVMFragment<FragmentGenresBinding, GenresViewModel>() {

    private val genresAdapter by lazy {
        GenresAdapter()
    }

    private val animeContainerAdapter by lazy {
        AnimeContainerAdapter(
            onSeeMoreClickListener = {

            },
            onItemClickListener = { item, _ ->
                handleOpenDetailScreen(item)
            }
        )
    }

    override fun getLayoutResId() = R.layout.fragment_genres

    override fun getViewModelClass(): Class<GenresViewModel> = GenresViewModel::class.java

    override fun onViewReady(savedInstanceState: Bundle?) {
        binding.rvGenres.adapter = genresAdapter
        binding.rvGenres.spacing {
            start = 4
            end = 4
            top = 4
            bottom = 4
        }

        binding.rvInfo.adapter = animeContainerAdapter
        binding.rvInfo.spacing {
            top = 32
            bottom = 16
        }
    }

    override fun initObserve() {
        binding.viewModel = viewModel

        observeLatest(viewModel.colorfulGenres) {
            genresAdapter.submitList(it)
        }

        observeLatest(viewModel.animeDisplayList) {
            animeContainerAdapter.submitList(it)
        }

        viewModel.getAnimeGenres()
        launchWhenCreated {
            delay(3000)
            viewModel.fetchAnimePopular()
        }
    }

    private fun handleOpenDetailScreen(item: DisplayAnimeInfo) {
        navigate(
            R.id.action_genresFragment_to_animeDetailFragment,
            bundleOf(
                Constants.Bundle.ID to item.getId()
            )
        )
    }
}