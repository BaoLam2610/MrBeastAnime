package com.lambao.mrbeast.presentation.ui.fragment.anime_search

import androidx.core.content.ContextCompat
import com.lambao.mrbeast.domain.model.Selectable
import com.lambao.mrbeast.domain.model.display.filter.AnimeFilter
import com.lambao.mrbeast.presentation.ui.common.adapter.SelectableAdapter
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.ItemAnimeFilterBinding

class AnimeFilterAdapter(
    onItemClickListener: (Selectable<AnimeFilter>, Int) -> Unit
) : SelectableAdapter<AnimeFilter, ItemAnimeFilterBinding>(
    areItemsTheSame = { old, new -> old.data.id == new.data.id },
    onItemClickListener = onItemClickListener
) {
    override fun getLayoutId(viewType: Int) = R.layout.item_anime_filter
    override fun bind(
        binding: ItemAnimeFilterBinding,
        item: Selectable<AnimeFilter>,
        position: Int
    ) {
        binding.item = item
        binding.tvTitle.setTextColor(
            ContextCompat.getColor(
                binding.root.context,
                if (item.data.selectors.isEmpty()) com.lambao.base.R.color.background_primary
                else com.lambao.base.R.color.text_secondary
            )
        )
        if (item.data.selectors.isNotEmpty()) {
            binding.tvTitle.setBackgroundResource(
                if (item.isSelected) R.drawable.bg_primary_stroke_corner_4
                else R.drawable.bg_stroke_corner_4
            )
        } else
            binding.tvTitle.setBackgroundDrawable(null)
    }
}