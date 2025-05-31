package com.lambao.mrbeast.domain.model.display.reaction

import androidx.annotation.DrawableRes
import com.lambao.mrbeast_anime.R

sealed class ReactionItem(
    val emoji: String = "",
    val count: Int,
    @DrawableRes val backgroundId: Int? = null,
) {
    class Overall(count: Int) : ReactionItem("\uD83C\uDF1F", count, R.drawable.bg_yellow_stroke_corner_1000)
    class Nice(count: Int) : ReactionItem("\uD83D\uDE0A", count, R.drawable.bg_blue_stroke_corner_1000)
    class LoveIt(count: Int) : ReactionItem("❤\uFE0F", count, R.drawable.bg_red_stroke_corner_1000)
    class Funny(count: Int) : ReactionItem("\uD83D\uDE02", count, R.drawable.bg_green_stroke_corner_1000)
    class Confusing(count: Int) : ReactionItem("\uD83E\uDD14", count, R.drawable.bg_orange_stroke_corner_1000)
    class Informative(count: Int) : ReactionItem("ℹ\uFE0F", count, R.drawable.bg_blue_stroke_corner_1000)
    class WellWritten(count: Int) : ReactionItem("✍\uFE0F", count, R.drawable.bg_pink_stroke_corner_1000)
    class Creative(count: Int) : ReactionItem("\uD83C\uDFA8", count, R.drawable.bg_purple_stroke_corner_1000)

    override fun toString(): String {
        return "$emoji $count"
    }
}