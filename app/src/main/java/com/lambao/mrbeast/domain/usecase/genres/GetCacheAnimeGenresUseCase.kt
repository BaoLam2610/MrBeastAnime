package com.lambao.mrbeast.domain.usecase.genres

import com.lambao.base.data.Resource
import com.lambao.base.domain.FlowUseCase
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.mrbeast.data.model.genres.Genres
import com.lambao.mrbeast.data.repository.genres.GenresRepository
import javax.inject.Inject

class GetCacheAnimeGenresUseCase @Inject constructor(
    private val genresRepository: GenresRepository,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<Unit, Resource<List<Genres>>>(dispatcherProvider) {
    override fun execute(params: Unit?) = genresRepository.getCacheAnimeGenres()
}