package com.lambao.mrbeast.presentation.ui.fragment.anime_detail.pictures

import com.lambao.presentation.ui.recycler_view.BaseDiffAdapter
import com.lambao.mrbeast.domain.model.display.DisplayAnimePictureInfo
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.ItemAnimePictureBinding

class AnimePicturesAdapter : BaseDiffAdapter<DisplayAnimePictureInfo, ItemAnimePictureBinding>(
    areItemsTheSame = { old, new -> old.getId() == new.getId() }
) {
    override fun getLayoutId(viewType: Int) = R.layout.item_anime_picture

    override fun bind(
        binding: ItemAnimePictureBinding,
        item: DisplayAnimePictureInfo,
        position: Int
    ) {
        binding.item = item
    }
}