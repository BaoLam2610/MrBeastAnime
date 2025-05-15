package com.lambao.mrbeast.presentation.ui.fragment.anime_detail.pictures

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.lambao.base.extension.getParcelableCompat
import com.lambao.base.extension.observeLatest
import com.lambao.base.presentation.ui.fragment.BaseVMFragment
import com.lambao.base.presentation.ui.view.recycler_view.spacing
import com.lambao.mrbeast.utils.Constants
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.FragmentAnimePicturesBinding
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
        binding.webView.apply {
            settings.javaScriptEnabled = true
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true

            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    return false
                }
            }
        }
    }

    override fun initObserve() {
        binding.viewModel = viewModel
        argData?.id?.let { viewModel.fetchAnimePictures(it) }
        argData?.trailer?.let { viewModel.setAnimeTrailer(it) }

        observeLatest(viewModel.shouldShowTrailer()) {  }

        observeLatest(viewModel.items) {
            picturesAdapter.submitList(it)
        }

        observeLatest(viewModel.getAnimeTrailer()) {
            it?.embedUrl?.let { url -> binding.webView.loadUrl(url) }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }
}