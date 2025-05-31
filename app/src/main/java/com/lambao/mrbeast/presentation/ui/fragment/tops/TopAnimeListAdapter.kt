package com.lambao.mrbeast.presentation.ui.fragment.tops

import com.lambao.base.presentation.ui.recycler_view.paging.BasePagingAdapter
import com.lambao.mrbeast.domain.model.display.DisplayTopAnimeInfo
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.ItemTopAnimeBinding

class TopAnimeListAdapter(
    onItemClickListener: (DisplayTopAnimeInfo, Int) -> Unit,
) : BasePagingAdapter<DisplayTopAnimeInfo, ItemTopAnimeBinding>(
    areItemsTheSame = { old, new -> old.getId() == new.getId() },
    onItemClickListener = onItemClickListener
) {
    override fun getLayoutId(viewType: Int) = R.layout.item_top_anime

    override fun bind(
        binding: ItemTopAnimeBinding,
        item: DisplayTopAnimeInfo,
        position: Int
    ) {
        binding.item = item
    }
}