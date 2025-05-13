package com.lambao.mrbeast.presentation.ui.fragment.home

import com.lambao.base.extension.click
import com.lambao.base.presentation.ui.recycler_view.BaseDiffAdapter
import com.lambao.mrbeast.domain.model.display.DisplayTopAnimeInfo
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.ItemTopAnimeSliderBinding

class TopAnimeSliderAdapter(
    private val onInfoClickListener: (DisplayTopAnimeInfo, Int) -> Unit
) : BaseDiffAdapter<DisplayTopAnimeInfo, ItemTopAnimeSliderBinding>(
    areItemsTheSame = { old, new -> old.getId() == new.getId() },
) {
    override fun getLayoutId(viewType: Int) = R.layout.item_top_anime_slider

    override fun bind(
        binding: ItemTopAnimeSliderBinding,
        item: DisplayTopAnimeInfo,
        position: Int
    ) {
        with(binding) {
            this.item = item
            btnInfo.click {
                onInfoClickListener.invoke(item, position)
            }
        }
    }
}