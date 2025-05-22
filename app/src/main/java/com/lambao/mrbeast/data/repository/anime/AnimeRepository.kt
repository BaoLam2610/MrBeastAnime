package com.lambao.mrbeast.data.repository.anime

import androidx.paging.PagingData
import com.lambao.base.data.Resource
import com.lambao.mrbeast.data.model.anime.Anime
import com.lambao.mrbeast.data.model.anime.AnimeCharacter
import com.lambao.mrbeast.data.model.anime.AnimeEpisode
import com.lambao.mrbeast.data.model.anime.AnimePicture
import com.lambao.mrbeast.data.model.anime.AnimeVideoEpisode
import com.lambao.mrbeast.data.remote.params.anime.AnimeParams
import com.lambao.mrbeast.data.remote.params.search.SearchParams
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {
    fun getAnimeFullById(params: AnimeParams): Flow<Resource<Anime>>
    fun getAnimeEpisodes(params: AnimeParams): Flow<Resource<List<AnimeEpisode>>>
    fun getAnimeVideosEpisodes(params: AnimeParams): Flow<Resource<List<AnimeVideoEpisode>>>
    fun getAnimePictures(params: AnimeParams): Flow<Resource<List<AnimePicture>>>
    fun getAnimeCharacters(params: AnimeParams): Flow<Resource<List<AnimeCharacter>>>
    fun getAnimeSearch(params: SearchParams): Flow<Resource<List<Anime>>>

    fun getAnimeEpisodesPaginated(params: AnimeParams): Flow<PagingData<AnimeEpisode>>
    fun getAnimeVideosEpisodesPaginated(params: AnimeParams): Flow<PagingData<AnimeVideoEpisode>>
    fun getAnimePicturesPaginated(params: AnimeParams): Flow<PagingData<AnimePicture>>
    fun getAnimeCharactersPaginated(params: AnimeParams): Flow<PagingData<AnimeCharacter>>
    fun getAnimeSearchPaginated(params: SearchParams): Flow<PagingData<Anime>>
}