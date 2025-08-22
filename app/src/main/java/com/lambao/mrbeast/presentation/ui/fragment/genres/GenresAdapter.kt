package com.lambao.mrbeast.presentation.ui.fragment.genres

import com.lambao.presentation.ui.recycler_view.BaseDiffAdapter
import com.lambao.mrbeast.domain.model.display.genre.ColorfulGenre
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.ItemGenresBinding

class GenresAdapter : BaseDiffAdapter<ColorfulGenre, ItemGenresBinding>() {
    override fun getLayoutId(viewType: Int) = R.layout.item_genres

    override fun bind(binding: ItemGenresBinding, item: ColorfulGenre, position: Int) {
        binding.item = item
    }
}