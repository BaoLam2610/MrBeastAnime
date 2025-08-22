package com.lambao.mrbeast.presentation.ui.fragment.genres

import android.os.Bundle
import com.lambao.presentation.extension.launchWhenCreated
import com.lambao.presentation.extension.observeLatest
import com.lambao.base.presentation.ui.fragment.BaseVMFragment
import com.lambao.base.presentation.ui.view.recycler_view.spacing
import com.lambao.mrbeast.presentation.ui.common.adapter.anime_info.AnimeContainerAdapter
import com.lambao.mrbeast.presentation.ui.common.navigator.NavigatorDelegate
import com.lambao.mrbeast.presentation.ui.common.navigator.NavigatorDelegateImpl
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.FragmentGenresBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class GenresFragment : BaseVMFragment<FragmentGenresBinding, GenresViewModel>() {

    private val navigator: NavigatorDelegate by lazy {
        NavigatorDelegateImpl(this)
    }

    private val genresAdapter by lazy {
        GenresAdapter()
    }

    private val animeContainerAdapter by lazy {
        AnimeContainerAdapter(
            onSeeMoreClickListener = {

            },
            onItemClickListener = { item, _ ->
                navigator.navigateGenreToDetail(item)
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
            if (viewModel.shouldLoadData().value) {
                viewModel.fetchAnimePopular()
                viewModel.setLoadData(false)
            }
        }
    }
}