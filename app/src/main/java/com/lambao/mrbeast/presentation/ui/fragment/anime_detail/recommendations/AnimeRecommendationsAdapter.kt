package com.lambao.mrbeast.presentation.ui.fragment.anime_detail.recommendations

import com.lambao.presentation.ui.recycler_view.BaseDiffAdapter
import com.lambao.mrbeast.domain.model.display.DisplayAnimeRecommendationInfo
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.ItemAnimeRecommendationBinding

class AnimeRecommendationsAdapter(
    onItemClickListener: (DisplayAnimeRecommendationInfo, Int) -> Unit
) : BaseDiffAdapter<DisplayAnimeRecommendationInfo, ItemAnimeRecommendationBinding>(
    onItemClickListener = onItemClickListener
) {

    override fun getLayoutId(viewType: Int) = R.layout.item_anime_recommendation

    override fun bind(
        binding: ItemAnimeRecommendationBinding,
        item: DisplayAnimeRecommendationInfo,
        position: Int
    ) {
        binding.item = item
    }
}