package com.lambao.mrbeast.domain.usecase.seasons

import com.lambao.base.domain.PagingUseCase
import com.lambao.base.domain.exception.ParamsEmptyException
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.mrbeast.data.model.seasons.SeasonAnime
import com.lambao.mrbeast.data.remote.params.seasons.SeasonsParams
import com.lambao.mrbeast.data.repository.seasons.SeasonsRepository
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSeasonNowPagingUseCase @Inject constructor(
    private val seasonsRepository: SeasonsRepository,
    dispatcherProvider: DispatcherProvider
) : PagingUseCase<SeasonsParams, SeasonAnime>(dispatcherProvider) {
    override fun execute(params: SeasonsParams?) = flow {
        if (params == null) {
            throw ParamsEmptyException()
        } else {
            emitAll(seasonsRepository.getSeasonNowPaginated(params))
        }
    }
}