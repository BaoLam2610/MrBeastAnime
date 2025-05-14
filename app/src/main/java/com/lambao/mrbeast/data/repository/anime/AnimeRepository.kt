package com.lambao.mrbeast.data.repository.anime

import com.lambao.base.data.Resource
import com.lambao.mrbeast.data.model.anime.Anime
import com.lambao.mrbeast.data.model.anime.AnimeEpisode
import com.lambao.mrbeast.data.model.anime.AnimePicture
import com.lambao.mrbeast.data.model.anime.AnimeVideoEpisode
import com.lambao.mrbeast.data.remote.params.anime.AnimeParams
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {
    fun getAnimeFullById(params: AnimeParams): Flow<Resource<Anime>>
    fun getAnimeEpisodes(params: AnimeParams): Flow<Resource<List<AnimeEpisode>>>
    fun getAnimeVideosEpisodes(params: AnimeParams): Flow<Resource<List<AnimeVideoEpisode>>>
    fun getAnimePictures(params: AnimeParams): Flow<Resource<List<AnimePicture>>>
}