package com.lambao.mrbeast.presentation.ui.fragment.anime_filter

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.lambao.presentation.extension.getParcelableCompat
import com.lambao.presentation.extension.observeLatest
import com.lambao.presentation.extension.popBackStack
import com.lambao.presentation.extension.setNavigationResult
import com.lambao.presentation.extension.toDp
import com.lambao.base.presentation.ui.view.recycler_view.spacing
import com.lambao.mrbeast.presentation.ui.common.adapter.TextSelectAdapter
import com.lambao.mrbeast.presentation.ui.fragment.base.filter.FilterBottomSheet
import com.lambao.mrbeast.utils.Constants
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.FragmentAnimeFilterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnimeFilterBottomSheet :
    FilterBottomSheet<FragmentAnimeFilterBinding, AnimeFilterViewModel>() {

    override val argData by lazy {
        arguments?.getParcelableCompat<AnimeFilterArgument>(Constants.Bundle.ARG)
    }

    private val selectAdapter by lazy {
        TextSelectAdapter { item, _ ->
            viewModel.onSelectItem(item.data)
            if (argData?.filterSelected != item.data) {
                setNavigationResult(Constants.Bundle.RESULT, viewModel.selectedFilter.value)
            }
            popBackStack()
        }
    }

    override fun getChildLayoutResId() = R.layout.fragment_anime_filter

    override fun getViewModelClass() = AnimeFilterViewModel::class.java

    override fun getTitleScreen() = argData?.title ?: ""

    override fun onChildViewReady(savedInstanceState: Bundle?) {
        childBinding.rvData.adapter = selectAdapter
        childBinding.rvData.spacing {
            top = 32
            bottom = 32
        }
        childBinding.rvData.addItemDecoration(
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
        childBinding.viewModel = viewModel

        observeLatest(viewModel.selectableFilters) {
            selectAdapter.submitList(it)
        }

        argData?.let {
            val filters = it.filters ?: emptyList()
            viewModel.setFilters(filters, it.filterSelected)

            if (filters.size > 5) {
                isCancelable = false
                setFull(true)
                childBinding.rvData.updatePadding(bottom = 100.toDp)
            }
        }
    }
}