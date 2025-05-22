package com.lambao.mrbeast.domain.usecase.anime

import com.lambao.base.domain.PagingUseCase
import com.lambao.base.domain.exception.ParamsEmptyException
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.mrbeast.data.model.anime.AnimePicture
import com.lambao.mrbeast.data.remote.params.anime.AnimeParams
import com.lambao.mrbeast.data.repository.anime.AnimeRepository
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAnimePicturesPagingUseCase @Inject constructor(
    private val animeRepository: AnimeRepository,
    dispatcherProvider: DispatcherProvider
) : PagingUseCase<AnimeParams, AnimePicture>(dispatcherProvider) {
    override fun execute(params: AnimeParams?) = flow {
        if (params == null) {
            throw ParamsEmptyException()
        } else {
            emitAll(animeRepository.getAnimePicturesPaginated(params))
        }
    }
}