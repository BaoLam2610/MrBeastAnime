package com.lambao.mrbeast.presentation.ui.fragment.anime_detail.videos_episodes

import com.lambao.presentation.ui.recycler_view.BaseDiffAdapter
import com.lambao.mrbeast.domain.model.display.DisplayAnimeVideoEpisodeInfo
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.ItemAnimeVideoEpisodeBinding

class AnimeVideosEpisodesAdapter(
    onItemClickListener: (DisplayAnimeVideoEpisodeInfo, Int) -> Unit
) : BaseDiffAdapter<DisplayAnimeVideoEpisodeInfo, ItemAnimeVideoEpisodeBinding>(onItemClickListener = onItemClickListener) {

    override fun getLayoutId(viewType: Int) = R.layout.item_anime_video_episode

    override fun bind(
        binding: ItemAnimeVideoEpisodeBinding,
        item: DisplayAnimeVideoEpisodeInfo,
        position: Int
    ) {
        binding.item = item
    }
}