package com.lambao.mrbeast.data.model.anime

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.lambao.mrbeast.data.model.Character
import com.lambao.mrbeast.data.model.VoiceActor
import com.lambao.mrbeast.domain.model.display.DisplayAnimeCharacterInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnimeCharacter(
    @Expose @SerializedName("character") val character: Character? = null,
    @Expose @SerializedName("role") val role: String? = null,
    @Expose @SerializedName("favorites") val favorites: Int? = null,
    @Expose @SerializedName("voice_actors") val voiceActors: List<VoiceActor>? = null
) : Parcelable, DisplayAnimeCharacterInfo {
    override fun getCharacterId() = character?.malId?.toString()

    override fun getVoiceActorId() = getJapaneseVoiceActor()?.person?.malId?.toString()

    override fun getJapaneseVoiceActor() = voiceActors?.find { it.language == "Japanese" }

    override fun displayCharacterName() = character?.name ?: ""

    override fun displayCharacterRole() = role ?: ""

    override fun displayCharacterImage() = character?.images?.jpg?.imageUrl ?: ""

    override fun displayFavoriteCount() = favorites?.toString() ?: ""

    override fun displayVoiceActorName() = getJapaneseVoiceActor()?.person?.name ?: ""

    override fun displayVoiceActorLanguage() = getJapaneseVoiceActor()?.language ?: ""

    override fun displayVoiceActorImage() =
        getJapaneseVoiceActor()?.person?.images?.jpg?.imageUrl ?: ""
}