package com.lambao.mrbeast.presentation.ui.fragment.home

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.lambao.base.presentation.ui.fragment.BaseVMFragment
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseVMFragment<FragmentHomeBinding, HomeViewModel>() {

    override fun getLayoutResId() = R.layout.fragment_home

    override fun getViewModelClass() = HomeViewModel::class.java

    override fun onViewReady(savedInstanceState: Bundle?) {
        binding.viewModel = viewModel
    }

    override fun initObserve() {
        viewModel.getTopAnime()
    }
}