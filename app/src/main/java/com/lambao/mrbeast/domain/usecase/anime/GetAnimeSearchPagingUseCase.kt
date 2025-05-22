package com.lambao.mrbeast.domain.usecase.anime

import com.lambao.base.domain.PagingUseCase
import com.lambao.base.domain.exception.ParamsEmptyException
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.mrbeast.data.model.anime.Anime
import com.lambao.mrbeast.data.remote.params.search.SearchParams
import com.lambao.mrbeast.data.repository.anime.AnimeRepository
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAnimeSearchPagingUseCase @Inject constructor(
    private val animeRepository: AnimeRepository,
    dispatchProvider: DispatcherProvider
) : PagingUseCase<SearchParams, Anime>(dispatchProvider) {
    override fun execute(params: SearchParams?) = flow {
        if (params == null) {
            throw ParamsEmptyException()
        } else {
            emitAll(animeRepository.getAnimeSearchPaginated(params))
        }
    }
}