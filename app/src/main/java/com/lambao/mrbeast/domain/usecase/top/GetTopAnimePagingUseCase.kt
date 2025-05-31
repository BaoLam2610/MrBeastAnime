package com.lambao.mrbeast.domain.usecase.top

import com.lambao.base.domain.PagingUseCase
import com.lambao.base.domain.exception.ParamsEmptyException
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.mrbeast.data.model.top.TopAnime
import com.lambao.mrbeast.data.remote.params.top.TopParams
import com.lambao.mrbeast.data.repository.top.TopRepository
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTopAnimePagingUseCase @Inject constructor(
    private val topRepository: TopRepository,
    dispatcherProvider: DispatcherProvider
) : PagingUseCase<TopParams, TopAnime>(dispatcherProvider) {
    override fun execute(params: TopParams?) = flow {
        if (params == null) {
            throw ParamsEmptyException()
        } else {
            emitAll(topRepository.getTopAnimePaginated(params))
        }
    }
}