package com.lambao.mrbeast.presentation.ui.fragment.anime_detail.pictures

import android.annotation.SuppressLint
import android.os.Bundle
import com.lambao.presentation.extension.getParcelableCompat
import com.lambao.presentation.extension.launchWhenCreated
import com.lambao.presentation.extension.observeLatest
import com.lambao.base.presentation.ui.fragment.paging.BaseManualPagingFragment
import com.lambao.base.presentation.ui.view.recycler_view.spacing
import com.lambao.mrbeast.utils.Constants
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.FragmentAnimePicturesBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnimePicturesFragment :
    BaseManualPagingFragment<FragmentAnimePicturesBinding, AnimePicturesViewModel>() {

    companion object {
        fun newInstance(data: AnimePicturesArgument?): AnimePicturesFragment {
            val args = Bundle().apply {
                putParcelable(Constants.Bundle.ARG, data)
            }

            val fragment = AnimePicturesFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val argData by lazy {
        arguments?.getParcelableCompat<AnimePicturesArgument>(Constants.Bundle.ARG)
    }

    private val picturesAdapter by lazy { AnimePicturesAdapter() }

    override fun getLayoutResId() = R.layout.fragment_anime_pictures

    override fun getViewModelClass() = AnimePicturesViewModel::class.java

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewReady(savedInstanceState: Bundle?) {
        binding.rvCharacters.adapter = picturesAdapter
        binding.rvCharacters.spacing {
            start = 8
            end = 8
        }
        lifecycle.addObserver(binding.youtubePlayer)
    }

    override fun initObserve() {
        binding.viewModel = viewModel

        observeLatest(viewModel.shouldShowTrailer())

        observeLatest(viewModel.items) {
            picturesAdapter.submitList(it)
        }

        binding.youtubePlayer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                viewModel.setYoutubePlayer(youTubePlayer)
                viewModel.loadYoutubeVideo(
                    viewModel.getAnimeTrailer().value
                        ?.youtubeId
                )
            }
        })

        launchWhenCreated {
            argData?.trailer?.let { viewModel.setAnimeTrailer(it) }
            argData?.id?.let { viewModel.fetchAnimePictures(it) }
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) {
            viewModel.pauseYoutubeVideo()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    override fun showLoading() = Unit

    override fun hideLoading() = Unit
}