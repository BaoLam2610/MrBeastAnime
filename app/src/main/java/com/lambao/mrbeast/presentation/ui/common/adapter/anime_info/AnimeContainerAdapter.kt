package com.lambao.mrbeast.presentation.ui.common.adapter.anime_info

import androidx.databinding.ViewDataBinding
import com.lambao.base.extension.click
import com.lambao.base.presentation.ui.recycler_view.BaseDiffMultiAdapter
import com.lambao.base.presentation.ui.view.recycler_view.linearSpacing
import com.lambao.mrbeast.domain.model.display.DisplayAnimeInfo
import com.lambao.mrbeast.domain.model.type.InfoType
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.ItemAnimeRecyclerBinding
import com.lambao.mrbeast_anime.databinding.ItemTitleSeeMoreBinding

class AnimeContainerAdapter(
    private val onSeeMoreClickListener: (InfoType) -> Unit,
    private val onItemClickListener: (DisplayAnimeInfo, Int) -> Unit
) : BaseDiffMultiAdapter<AnimeItem>() {
    companion object {
        const val TYPE_TITLE = 0
        const val TYPE_BODY = 1
    }

    override fun getViewType(
        item: AnimeItem,
        position: Int
    ): Int {
        return when (item) {
            is AnimeItem.Title -> TYPE_TITLE
            is AnimeItem.Body -> TYPE_BODY
        }
    }

    override fun getLayoutId(viewType: Int): Int {
        return when (viewType) {
            TYPE_TITLE -> R.layout.item_title_see_more
            TYPE_BODY -> R.layout.item_anime_recycler
            else -> R.layout.item_title_see_more
        }
    }

    override fun bind(
        binding: ViewDataBinding,
        item: AnimeItem,
        position: Int
    ) {
        when (binding) {
            is ItemTitleSeeMoreBinding -> {
                val itemTitle = item as AnimeItem.Title
                binding.title = itemTitle.text
                binding.btnSeeMore.click {
                    onSeeMoreClickListener.invoke(itemTitle.type)
                }
            }

            is ItemAnimeRecyclerBinding -> {
                val itemData = item as AnimeItem.Body
                val infoAdapter = AnimeInfoAdapter(onItemClickListener).apply {
                    submitList(itemData.data)
                }
                binding.rvInfo.apply {
                    setHasFixedSize(true)
                    adapter = infoAdapter
                    linearSpacing {
                        start = 8
                        end = 8
                    }
                }
            }
        }
    }
}