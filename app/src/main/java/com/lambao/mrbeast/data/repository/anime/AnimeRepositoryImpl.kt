package com.lambao.mrbeast.data.repository.anime

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.google.gson.Gson
import com.lambao.base.data.remote.BaseRemoteDataSource
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.mrbeast.data.remote.paging.AnimeCharactersPagingSource
import com.lambao.mrbeast.data.remote.paging.AnimeEpisodesPagingSource
import com.lambao.mrbeast.data.remote.paging.AnimePicturesPagingSource
import com.lambao.mrbeast.data.remote.paging.AnimeReviewsPagingSource
import com.lambao.mrbeast.data.remote.paging.AnimeSearchPagingSource
import com.lambao.mrbeast.data.remote.paging.AnimeVideosEpisodesPagingSource
import com.lambao.mrbeast.data.remote.params.anime.AnimeParams
import com.lambao.mrbeast.data.remote.params.anime.AnimeReviewParams
import com.lambao.mrbeast.data.remote.params.search.SearchParams
import com.lambao.mrbeast.data.remote.service.AnimeService
import javax.inject.Inject

class AnimeRepositoryImpl @Inject constructor(
    private val animeService: AnimeService,
    gson: Gson,
    dispatcherProvider: DispatcherProvider
) : BaseRemoteDataSource(gson, dispatcherProvider), AnimeRepository {
    override fun getAnimeFullById(params: AnimeParams) = safeApiCall {
        animeService.getAnimeFullById(params.id)
    }

    override fun getAnimeEpisodes(params: AnimeParams) = safeApiCall {
        animeService.getAnimeEpisodes(params.id, params.toQueryMap())
    }

    override fun getAnimeVideosEpisodes(params: AnimeParams) = safeApiCall {
        animeService.getAnimeVideosEpisodes(params.id, params.toQueryMap())
    }

    override fun getAnimePictures(params: AnimeParams) = safeApiCall {
        animeService.getAnimePictures(params.id)
    }

    override fun getAnimeCharacters(params: AnimeParams) = safeApiCall {
        animeService.getAnimeCharacters(params.id)
    }

    override fun getAnimeRecommendations(params: AnimeParams) = safeApiCall {
        animeService.getAnimeRecommendations(params.id)
    }

    override fun getAnimeReviews(params: AnimeReviewParams) = safeApiCall {
        animeService.getAnimeReviews(params.id, params.toQueryMap())
    }

    override fun getAnimeStatistics(params: AnimeParams) = safeApiCall {
        animeService.getAnimeStatistics(params.id)
    }

    override fun getAnimeSearch(params: SearchParams) = safeApiCall {
        animeService.getAnimeSearch(params.toQueryMap())
    }

    override fun getAnimeEpisodesPaginated(params: AnimeParams) = Pager(
        config = PagingConfig(
            pageSize = params.limit,
            initialLoadSize = params.limit,
            prefetchDistance = 5,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            AnimeEpisodesPagingSource(this, params)
        }
    ).flow

    override fun getAnimeVideosEpisodesPaginated(params: AnimeParams) = Pager(
        config = PagingConfig(
            pageSize = params.limit,
            initialLoadSize = params.limit,
            prefetchDistance = 5,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            AnimeVideosEpisodesPagingSource(this, params)
        }
    ).flow

    override fun getAnimePicturesPaginated(params: AnimeParams) = Pager(
        config = PagingConfig(
            pageSize = params.limit,
            initialLoadSize = params.limit,
            prefetchDistance = 5,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            AnimePicturesPagingSource(this, params)
        }
    ).flow

    override fun getAnimeCharactersPaginated(params: AnimeParams) = Pager(
        config = PagingConfig(
            pageSize = params.limit,
            initialLoadSize = params.limit,
            prefetchDistance = 5,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            AnimeCharactersPagingSource(this, params)
        }
    ).flow

    override fun getAnimeReviewsPaginated(params: AnimeReviewParams) = Pager(
        config = PagingConfig(
            pageSize = 20,
            initialLoadSize = 20,
            prefetchDistance = 5,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            AnimeReviewsPagingSource(this, params)
        }
    ).flow

    override fun getAnimeSearchPaginated(params: SearchParams) = Pager(
        config = PagingConfig(
            pageSize = params.limit,
            initialLoadSize = params.limit,
            prefetchDistance = 5,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            AnimeSearchPagingSource(this, params)
        }
    ).flow
}