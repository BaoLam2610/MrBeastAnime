package com.lambao.mrbeast.presentation.ui.common.adapter

import androidx.core.content.ContextCompat
import com.lambao.mrbeast.domain.model.DisplayText
import com.lambao.mrbeast.domain.model.Selectable
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.ItemTextSelectBinding

class TextSelectAdapter<T : DisplayText>(
    onItemClickListener: ((Selectable<T>, Int) -> Unit)? = null
) : SelectableAdapter<T, ItemTextSelectBinding>(
    areItemsTheSame = { old, new -> old.data.id == new.data.id },
    onItemClickListener = onItemClickListener
) {
    override fun getLayoutId(viewType: Int) = R.layout.item_text_select

    override fun bind(binding: ItemTextSelectBinding, item: Selectable<T>, position: Int) {
        binding.item = item
        binding.tvTitle.text = item.data.displayText
        binding.tvTitle.setTextColor(
            ContextCompat.getColor(
                binding.root.context,
                if (item.isSelected) com.lambao.base.R.color.background_primary
                else item.data.getTextColor()
            )
        )
    }
}