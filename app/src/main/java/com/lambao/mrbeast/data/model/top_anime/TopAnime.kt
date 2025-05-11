package com.lambao.mrbeast.data.model.top_anime

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.lambao.mrbeast.data.model.Aired
import com.lambao.mrbeast.data.model.Broadcast
import com.lambao.mrbeast.data.model.Info
import com.lambao.mrbeast.data.model.Titles
import com.lambao.mrbeast.data.model.Trailer
import com.lambao.mrbeast.data.model.images.ImagesRemote
import com.lambao.mrbeast.domain.model.display.DisplayTopAnimeInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class TopAnime(
    @Expose @SerializedName("mal_id") val malId: Int? = null,
    @Expose @SerializedName("url") val url: String? = null,
    @Expose @SerializedName("images") val images: ImagesRemote? = null,
    @Expose @SerializedName("trailer") val trailer: Trailer? = null,
    @Expose @SerializedName("approved") val approved: Boolean? = null,
    @Expose @SerializedName("titles") val titles: List<Titles>? = null,
    @Expose @SerializedName("title") val title: String? = null,
    @Expose @SerializedName("title_english") val titleEnglish: String? = null,
    @Expose @SerializedName("title_japanese") val titleJapanese: String? = null,
    @Expose @SerializedName("title_synonyms") val titleSynonyms: List<String>? = null,
    @Expose @SerializedName("type") val type: String? = null,
    @Expose @SerializedName("source") val source: String? = null,
    @Expose @SerializedName("episodes") val episodes: Int? = null,
    @Expose @SerializedName("status") val status: String? = null,
    @Expose @SerializedName("airing") val airing: Boolean? = null,
    @Expose @SerializedName("aired") val aired: Aired? = null,
    @Expose @SerializedName("duration") val duration: String? = null,
    @Expose @SerializedName("rating") val rating: String? = null,
    @Expose @SerializedName("score") val score: Double? = null,
    @Expose @SerializedName("scored_by") val scoredBy: Int? = null,
    @Expose @SerializedName("rank") val rank: Int? = null,
    @Expose @SerializedName("popularity") val popularity: Int? = null,
    @Expose @SerializedName("members") val members: Int? = null,
    @Expose @SerializedName("favorites") val favorites: Int? = null,
    @Expose @SerializedName("synopsis") val synopsis: String? = null,
    @Expose @SerializedName("background") val background: String? = null,
    @Expose @SerializedName("season") val season: String? = null,
    @Expose @SerializedName("year") val year: Int? = null,
    @Expose @SerializedName("broadcast") val broadcast: Broadcast? = null,
    @Expose @SerializedName("producers") val producers: List<Info>? = null,
    @Expose @SerializedName("licensors") val licensors: List<Info>? = null,
    @Expose @SerializedName("studios") val studios: List<Info>? = null,
    @Expose @SerializedName("genres") val genres: List<Info>? = null,
    @Expose @SerializedName("explicit_genres") val explicitGenres: List<Info>? = null,
    @Expose @SerializedName("themes") val themes: List<Info>? = null,
    @Expose @SerializedName("demographics") val demographics: List<Info>? = null
) : Parcelable, DisplayTopAnimeInfo {
    override fun getId() = malId?.toString()

    override fun displayTitle() = title ?: ""

    override fun displayGenres(): String {
        if (genres.isNullOrEmpty()) return ""
        return genres.joinToString { it.name ?: "" }
    }

    override fun displayScore() = score?.toString() ?: ""

    override fun displayThumbnail() = images?.jpg?.largeImageUrl ?: ""
}