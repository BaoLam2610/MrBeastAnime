package com.lambao.mrbeast.presentation.ui.fragment.anime_search

import com.lambao.base.presentation.ui.recycler_view.paging.BasePagingAdapter
import com.lambao.mrbeast.domain.model.display.DisplayAnimeFullInfo
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.ItemAnimeSearchBinding

class AnimeSearchAdapter(
    onItemClickListener: (DisplayAnimeFullInfo, Int) -> Unit,
) : BasePagingAdapter<DisplayAnimeFullInfo, ItemAnimeSearchBinding>(
    areItemsTheSame = { old, new -> old.getId() == new.getId() },
    onItemClickListener = onItemClickListener
) {
    override fun getLayoutId(viewType: Int) = R.layout.item_anime_search

    override fun bind(
        binding: ItemAnimeSearchBinding,
        item: DisplayAnimeFullInfo,
        position: Int
    ) {
        binding.item = item
    }
}