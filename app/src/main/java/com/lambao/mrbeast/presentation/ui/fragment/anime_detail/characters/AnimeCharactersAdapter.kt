package com.lambao.mrbeast.presentation.ui.fragment.anime_detail.characters

import com.lambao.base.presentation.ui.recycler_view.BaseDiffAdapter
import com.lambao.mrbeast.domain.model.display.DisplayAnimeCharacterInfo
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.ItemAnimeCharacterBinding

class AnimeCharactersAdapter :
    BaseDiffAdapter<DisplayAnimeCharacterInfo, ItemAnimeCharacterBinding>(
        areItemsTheSame = { old, new -> old.getCharacterId() == new.getCharacterId() }
    ) {
    override fun getLayoutId(viewType: Int) = R.layout.item_anime_character

    override fun bind(
        binding: ItemAnimeCharacterBinding,
        item: DisplayAnimeCharacterInfo,
        position: Int
    ) {
        binding.item = item
    }
}