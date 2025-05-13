package com.lambao.mrbeast.domain.usecase.genres

import com.lambao.base.data.Resource
import com.lambao.base.domain.FlowUseCase
import com.lambao.base.domain.exception.ParamsEmptyException
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.mrbeast.data.model.genres.Genres
import com.lambao.mrbeast.data.repository.genres.GenresRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAnimeGenresUseCase @Inject constructor(
    private val genresRepository: GenresRepository,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<String, Resource<List<Genres>>>(dispatcherProvider) {
    override fun execute(params: String?) = params?.let {
        genresRepository.getAnimeGenres(it)
    } ?: flow {
        emit(Resource.Error(throwable = ParamsEmptyException()))
    }
}