package com.lambao.mrbeast.domain.usecase

import com.lambao.base.data.Resource
import com.lambao.base.domain.FlowUseCase
import com.lambao.base.domain.exception.ParamsEmptyException
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.mrbeast.data.model.top_anime.TopAnime
import com.lambao.mrbeast.data.remote.params.top_anime.TopAnimeParams
import com.lambao.mrbeast.data.repository.top.TopRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTopAnimeUseCase @Inject constructor(
    private val topRepository: TopRepository,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<TopAnimeParams, Resource<List<TopAnime>>>(dispatcherProvider) {
    override fun execute(params: TopAnimeParams?) = params?.let {
        topRepository.getTopAnime(it)
    } ?: flow {
        emit(Resource.Error(throwable = ParamsEmptyException()))
    }
}