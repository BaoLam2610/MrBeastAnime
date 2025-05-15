package com.lambao.mrbeast.presentation.ui.common.view_model.anime

import com.lambao.mrbeast.data.model.anime.Anime
import kotlinx.coroutines.flow.StateFlow

interface AnimeDelegate {
    fun setAnime(anime: Anime?)
    fun setAnimeId(id: String)
    fun getAnime(): StateFlow<Anime?>
    fun getAnimeId(): StateFlow<String>
}