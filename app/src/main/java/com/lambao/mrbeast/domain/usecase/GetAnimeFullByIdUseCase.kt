package com.lambao.mrbeast.domain.usecase

import com.lambao.base.data.Resource
import com.lambao.base.domain.FlowUseCase
import com.lambao.base.domain.exception.ParamsEmptyException
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.mrbeast.data.model.anime.Anime
import com.lambao.mrbeast.data.repository.anime.AnimeRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAnimeFullByIdUseCase @Inject constructor(
    private val animeRepository: AnimeRepository,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<String, Resource<Anime>>(dispatcherProvider) {
    override fun execute(params: String?) = params?.let {
        animeRepository.getAnimeFullById(it)
    } ?: flow {
        emit(Resource.Error(throwable = ParamsEmptyException()))
    }
}