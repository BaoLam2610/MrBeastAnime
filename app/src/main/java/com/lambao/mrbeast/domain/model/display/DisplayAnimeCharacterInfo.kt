package com.lambao.mrbeast.domain.model.display

import com.lambao.mrbeast.data.model.VoiceActor

interface DisplayAnimeCharacterInfo {
    fun getCharacterId(): String?
    fun getVoiceActorId(): String?
    fun getJapaneseVoiceActor(): VoiceActor?

    fun displayCharacterName(): String
    fun displayCharacterRole(): String
    fun displayCharacterImage(): String
    fun displayFavoriteCount(): String
    fun displayVoiceActorName(): String
    fun displayVoiceActorLanguage(): String
    fun displayVoiceActorImage(): String

    fun shouldDisplayCharacterName() = displayCharacterName().isNotEmpty()
    fun shouldDisplayCharacterRole() = displayCharacterRole().isNotEmpty()
    fun shouldDisplayCharacterImage() = displayCharacterImage().isNotEmpty()
    fun shouldDisplayFavoriteCount() = displayFavoriteCount().isNotEmpty()
    fun shouldDisplayVoiceActorName() = displayVoiceActorName().isNotEmpty()
    fun shouldDisplayVoiceActorLanguage() = displayVoiceActorLanguage().isNotEmpty()
    fun shouldDisplayVoiceActorImage() = displayVoiceActorImage().isNotEmpty()
}