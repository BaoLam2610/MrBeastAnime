package com.lambao.mrbeast.presentation.ui.fragment.anime_detail.episodes

import com.lambao.presentation.ui.recycler_view.BaseDiffAdapter
import com.lambao.mrbeast.domain.model.display.DisplayAnimeEpisodeInfo
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.ItemAnimeEpisodeBinding

class AnimeEpisodesAdapter(
    onItemClickListener: (DisplayAnimeEpisodeInfo, Int) -> Unit
) : BaseDiffAdapter<DisplayAnimeEpisodeInfo, ItemAnimeEpisodeBinding>(onItemClickListener = onItemClickListener) {

    override fun getLayoutId(viewType: Int) = R.layout.item_anime_episode

    override fun bind(
        binding: ItemAnimeEpisodeBinding,
        item: DisplayAnimeEpisodeInfo,
        position: Int
    ) {
        binding.item = item
    }
}