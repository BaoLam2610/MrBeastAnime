package com.lambao.mrbeast.domain.usecase.top

import com.lambao.base.data.Resource
import com.lambao.base.domain.FlowUseCase
import com.lambao.base.domain.exception.ParamsEmptyException
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.mrbeast.data.model.top.TopAnime
import com.lambao.mrbeast.data.remote.params.top.TopParams
import com.lambao.mrbeast.data.repository.top.TopRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTopAnimeUseCase @Inject constructor(
    private val topRepository: TopRepository,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<TopParams, Resource<List<TopAnime>>>(dispatcherProvider) {
    override fun execute(params: TopParams?) = params?.let {
        topRepository.getTopAnime(it)
    } ?: flow {
        emit(Resource.Error(throwable = ParamsEmptyException()))
    }
}