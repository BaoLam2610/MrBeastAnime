package com.lambao.mrbeast.presentation.ui.common.view_model.anime

import com.lambao.mrbeast.data.model.Trailer
import com.lambao.mrbeast.data.model.anime.Anime
import kotlinx.coroutines.flow.StateFlow

interface AnimeDelegate {
    fun setAnime(anime: Anime?)
    fun setAnimeId(id: String)
    fun setAnimeTrailer(trailer: Trailer?)
    fun getAnime(): StateFlow<Anime?>
    fun getAnimeId(): StateFlow<String>
    fun getAnimeTrailer(): StateFlow<Trailer?>

    fun setShowTrailer(isShow: Boolean)
    fun shouldShowTrailer(): StateFlow<Boolean>
}