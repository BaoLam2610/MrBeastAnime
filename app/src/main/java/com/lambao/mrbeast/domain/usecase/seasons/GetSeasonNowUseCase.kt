package com.lambao.mrbeast.domain.usecase.seasons

import com.lambao.base.data.Resource
import com.lambao.base.domain.exception.ParamsEmptyException
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.mrbeast.data.remote.params.seasons.SeasonsParams
import com.lambao.mrbeast.data.repository.seasons.SeasonsRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSeasonNowUseCase @Inject constructor(
    private val seasonsRepository: SeasonsRepository,
    dispatcherProvider: DispatcherProvider
) : SeasonAnimeUseCase(dispatcherProvider) {
    override fun execute(params: SeasonsParams?) = params?.let {
        seasonsRepository.getSeasonNow(it)
    } ?: flow {
        emit(Resource.Error(throwable = ParamsEmptyException()))
    }
}