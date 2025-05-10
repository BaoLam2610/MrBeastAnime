package com.lambao.mrbeast.domain.usecase

import com.lambao.base.data.Resource
import com.lambao.base.domain.FlowUseCase
import com.lambao.base.domain.exception.ParamsEmptyException
import com.lambao.base.extension.asFlow
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.mrbeast.data.model.top_anime.TopAnime
import com.lambao.mrbeast.data.remote.request.top_anime.TopAnimeRequest
import com.lambao.mrbeast.data.repository.top.TopRepository
import javax.inject.Inject

class GetTopAnimeUseCase @Inject constructor(
    private val topRepository: TopRepository,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<TopAnimeRequest, Resource<List<TopAnime>>>(dispatcherProvider) {
    override fun execute(params: TopAnimeRequest?) = params?.let {
        topRepository.getTopAnime(it)
    } ?: run {
        ParamsEmptyException().asFlow()
    }
}