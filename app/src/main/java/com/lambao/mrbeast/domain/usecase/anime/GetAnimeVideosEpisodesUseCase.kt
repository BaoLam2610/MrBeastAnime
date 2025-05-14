package com.lambao.mrbeast.domain.usecase.anime

import com.lambao.base.data.Resource
import com.lambao.base.domain.FlowUseCase
import com.lambao.base.domain.exception.ParamsEmptyException
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.mrbeast.data.model.anime.AnimeVideoEpisode
import com.lambao.mrbeast.data.remote.params.anime.AnimeParams
import com.lambao.mrbeast.data.repository.anime.AnimeRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAnimeVideosEpisodesUseCase @Inject constructor(
    private val animeRepository: AnimeRepository,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<AnimeParams, Resource<List<AnimeVideoEpisode>>>(dispatcherProvider) {
    override fun execute(params: AnimeParams?) = params?.let {
        animeRepository.getAnimeVideosEpisodes(it)
    } ?: flow {
        emit(Resource.Error(throwable = ParamsEmptyException()))
    }
}