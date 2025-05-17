package com.lambao.mrbeast.presentation.ui.fragment.seasons

import com.lambao.base.presentation.ui.recycler_view.BaseDiffAdapter
import com.lambao.mrbeast.domain.model.display.DisplaySeasonAnimeInfo
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.ItemSeasonAnimeBinding

class SeasonAnimeListAdapter(
    onItemClickListener: (DisplaySeasonAnimeInfo, Int) -> Unit,
) : BaseDiffAdapter<DisplaySeasonAnimeInfo, ItemSeasonAnimeBinding>(
    areItemsTheSame = { old, new -> old.getId() == new.getId() },
    onItemClickListener = onItemClickListener
) {
    override fun getLayoutId(viewType: Int) = R.layout.item_season_anime

    override fun bind(
        binding: ItemSeasonAnimeBinding,
        item: DisplaySeasonAnimeInfo,
        position: Int
    ) {
        binding.item = item
    }
}