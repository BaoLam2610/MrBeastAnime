package com.lambao.mrbeast.presentation.ui.fragment.anime_rating.reviews

import com.lambao.base.presentation.ui.recycler_view.BaseRecyclerAdapter
import com.lambao.mrbeast.domain.model.display.reaction.ReactionItem
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.ItemReactionBinding

class ReactionAdapter : BaseRecyclerAdapter<ReactionItem, ItemReactionBinding>() {
    override fun getLayoutId(viewType: Int) = R.layout.item_reaction
    override fun bind(binding: ItemReactionBinding, item: ReactionItem, position: Int) {
        binding.item = item
    }
}