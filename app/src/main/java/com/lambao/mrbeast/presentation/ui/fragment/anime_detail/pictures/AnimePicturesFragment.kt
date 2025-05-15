package com.lambao.mrbeast.presentation.ui.fragment.anime_detail.pictures

import android.annotation.SuppressLint
import android.os.Bundle
import com.lambao.base.extension.getParcelableCompat
import com.lambao.base.extension.observeLatest
import com.lambao.base.presentation.ui.fragment.BaseVMFragment
import com.lambao.base.presentation.ui.view.recycler_view.spacing
import com.lambao.mrbeast.utils.Constants
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.FragmentAnimePicturesBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnimePicturesFragment :
    BaseVMFragment<FragmentAnimePicturesBinding, AnimePicturesViewModel>() {

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
        argData?.id?.let { viewModel.fetchAnimePictures(it) }
        argData?.trailer?.let { viewModel.setAnimeTrailer(it) }

        observeLatest(viewModel.shouldShowTrailer()) { }

        observeLatest(viewModel.items) {
            picturesAdapter.submitList(it)
        }

        binding.youtubePlayer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                viewModel.getAnimeTrailer().value
                    ?.youtubeId
                    ?.let {
                        youTubePlayer.loadVideo(it, 0f)
                    }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }
}