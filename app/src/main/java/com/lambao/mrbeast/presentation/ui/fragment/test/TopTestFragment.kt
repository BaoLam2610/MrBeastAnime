package com.lambao.mrbeast.presentation.ui.fragment.test

import android.os.Bundle
import com.lambao.base.extension.observeLatest
import com.lambao.base.presentation.ui.fragment.BaseVMFragment
import com.lambao.base.presentation.ui.recycler_view.paging.DefaultLoadStateAdapter
import com.lambao.mrbeast.presentation.ui.fragment.tops.TopAnimeListAdapter
import com.lambao.mrbeast.presentation.ui.fragment.tops.TopAnimeListViewModel
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.FragmentTopAnimeListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopTestFragment : BaseVMFragment<FragmentTopAnimeListBinding, TopAnimeListViewModel>() {

    private val adapter by lazy {
        TopAnimeListAdapter { item, _ ->

        }
    }

    private val loadStateAdapter by lazy {
        DefaultLoadStateAdapter {
            adapter.retry()
        }
    }

    override fun getLayoutResId() = R.layout.fragment_top_anime_list

    override fun getViewModelClass() = TopAnimeListViewModel::class.java

    override fun onViewReady(savedInstanceState: Bundle?) {
        binding.rvData.adapter = adapter.withLoadStateFooter(loadStateAdapter)
    }

    override fun initObserve() {
        observeLatest(viewModel.getTopAnimePaginated()) {
            adapter.submitData(it)
        }

        adapter.addLoadStateListener {
            viewModel.handleLoadStates(it, adapter.itemCount)
        }
    }
}