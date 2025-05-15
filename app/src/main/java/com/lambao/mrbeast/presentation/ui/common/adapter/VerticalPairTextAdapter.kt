package com.lambao.mrbeast.presentation.ui.common.adapter

import com.lambao.base.presentation.ui.recycler_view.BaseDiffAdapter
import com.lambao.mrbeast.domain.model.PairText
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.ItemVerticalPairTextBinding

class VerticalPairTextAdapter<T : PairText> : BaseDiffAdapter<T, ItemVerticalPairTextBinding>(
    areItemsTheSame = { old, new ->
        old.getKeyText() == new.getKeyText() &&
                old.getValueText() == new.getValueText() &&
                old.getKeyTextColor() == new.getKeyTextColor() &&
                old.getValueTextColor() == new.getValueTextColor()
    },
    areContentsTheSame = { old, new -> old == new }
) {
    override fun getLayoutId(viewType: Int) = R.layout.item_vertical_pair_text

    override fun bind(
        binding: ItemVerticalPairTextBinding,
        item: T,
        position: Int
    ) {
        binding.item = item
    }
}