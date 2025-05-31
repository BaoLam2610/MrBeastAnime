package com.lambao.mrbeast.domain.usecase.anime

import com.lambao.base.data.Resource
import com.lambao.base.domain.FlowUseCase
import com.lambao.base.domain.exception.ParamsEmptyException
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.mrbeast.data.model.anime.AnimeRecommendation
import com.lambao.mrbeast.data.remote.params.anime.AnimeParams
import com.lambao.mrbeast.data.repository.anime.AnimeRepository
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAnimeRecommendationsUseCase @Inject constructor(
    private val animeRepository: AnimeRepository,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<AnimeParams, Resource<List<AnimeRecommendation>>>(dispatcherProvider) {
    override fun execute(params: AnimeParams?) = flow {
        if (params == null)
            throw ParamsEmptyException()
        else
            emitAll(animeRepository.getAnimeRecommendations(params))
    }
}