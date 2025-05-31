package com.lambao.mrbeast.presentation.ui.fragment.anime_rating.reviews

import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.lambao.base.presentation.ui.recycler_view.paging.BasePagingAdapter
import com.lambao.base.presentation.ui.view.recycler_view.spacing
import com.lambao.mrbeast.domain.model.display.DisplayAnimeReviewInfo
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.ItemAnimeReviewBinding

class AnimeReviewsAdapter(
    onItemClickListener: ((DisplayAnimeReviewInfo, Int) -> Unit)? = null,
) : BasePagingAdapter<DisplayAnimeReviewInfo, ItemAnimeReviewBinding>(
    areItemsTheSame = { old, new -> old.id == new.id },
    onItemClickListener = onItemClickListener
) {
    override fun getLayoutId(viewType: Int) = R.layout.item_anime_review

    override fun bind(
        binding: ItemAnimeReviewBinding,
        item: DisplayAnimeReviewInfo,
        position: Int
    ) {
        binding.item = item

        val reactions = item.getReactionItems()
        if (reactions.isEmpty()) return

        val reactionAdapter = ReactionAdapter()
        reactionAdapter.submitList(reactions.filter { it.count > 0 })
        binding.rvReactions.adapter = reactionAdapter
        binding.rvReactions.setHasFixedSize(true)
        binding.rvReactions.spacing {
            top = 8
            bottom = 8
            start = 8
            end = 8
        }
        val layoutManager = FlexboxLayoutManager(binding.root.context)
        layoutManager.flexDirection = FlexDirection.ROW
        binding.rvReactions.setLayoutManager(layoutManager)
    }
}