package com.lambao.mrbeast.presentation.common.anime_info

import com.lambao.base.presentation.ui.recycler_view.BaseDiffAdapter
import com.lambao.mrbeast.domain.model.display.DisplayAnimeInfo
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.ItemAnimeInfoBinding

class AnimeInfoAdapter(
    onItemClickListener: (DisplayAnimeInfo, Int) -> Unit
) : BaseDiffAdapter<DisplayAnimeInfo, ItemAnimeInfoBinding>(
    areItemsTheSame = { oldItem, newItem -> oldItem.getId() == newItem.getId() },
    onItemClickListener = onItemClickListener
) {
    override fun getLayoutId(viewType: Int) = R.layout.item_anime_info

    override fun bind(
        binding: ItemAnimeInfoBinding,
        item: DisplayAnimeInfo,
        position: Int
    ) {
        binding.item = item
    }
}