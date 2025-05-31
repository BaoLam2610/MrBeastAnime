package com.lambao.mrbeast.data.model.anime

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.lambao.base.extension.reformatDate
import com.lambao.mrbeast.data.model.Reaction
import com.lambao.mrbeast.data.model.User
import com.lambao.mrbeast.domain.model.display.DisplayAnimeReviewInfo
import com.lambao.mrbeast.domain.model.display.reaction.ReactionItem
import com.lambao.mrbeast.utils.Constants
import kotlinx.parcelize.Parcelize
import java.util.Locale

@Parcelize
data class AnimeReview(
    @Expose @SerializedName("mal_id") val malId: Int? = null,
    @Expose @SerializedName("url") val url: String? = null,
    @Expose @SerializedName("type") val type: String? = null,
    @Expose @SerializedName("reactions") val reactions: Reaction? = null,
    @Expose @SerializedName("date") val date: String? = null,
    @Expose @SerializedName("review") val review: String? = null,
    @Expose @SerializedName("tags") val tags: List<String>? = null,
    @Expose @SerializedName("score") val score: Int? = null,
    @Expose @SerializedName("is_spoiler") val isSpoiler: Boolean? = null,
    @Expose @SerializedName("is_preliminary") val isPreliminary: Boolean? = null,
    @Expose @SerializedName("episodes_watched") val episodesWatched: Int? = null,
    @Expose @SerializedName("user") val user: User? = null,
) : Parcelable, DisplayAnimeReviewInfo {
    override val id: String? get() = malId?.toString()

    override fun getReactionItems() = buildList {
        reactions?.overall?.let {
            add(ReactionItem.Overall(count = it))
        }

        reactions?.nice?.let {
            add(ReactionItem.Nice(count = it))
        }

        reactions?.loveIt?.let {
            add(ReactionItem.LoveIt(count = it))
        }

        reactions?.funny?.let {
            add(ReactionItem.Funny(count = it))
        }

        reactions?.confusing?.let {
            add(ReactionItem.Confusing(count = it))
        }

        reactions?.informative?.let {
            add(ReactionItem.Informative(count = it))
        }

        reactions?.wellWritten?.let {
            add(ReactionItem.WellWritten(count = it))
        }

        reactions?.creative?.let {
            add(ReactionItem.Creative(count = it))
        }
    }

    override fun displayUsername() = user?.username ?: ""

    override fun displayUserAvatar() = user?.images?.jpg?.imageUrl ?: ""

    override fun displayUserScoreRated() = score?.toString() ?: ""

    override fun displayReviewDate() = date?.reformatDate(
        Constants.DateTime.yyyyMMddTHHmmssZ,
        Constants.DateTime.ddMMyyyyHHmm,
        Locale.US
    ) ?: ""

    override fun displayUserReview() = review ?: ""
}
