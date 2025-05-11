package com.lambao.mrbeast.domain.usecase

import com.lambao.base.data.Resource
import com.lambao.base.domain.FlowUseCase
import com.lambao.base.domain.exception.ParamsEmptyException
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.mrbeast.data.model.seasons.SeasonAnime
import com.lambao.mrbeast.data.remote.params.seasons.SeasonsParams
import com.lambao.mrbeast.data.repository.seasons.SeasonsRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSeasonUpcomingUseCase @Inject constructor(
    private val seasonsRepository: SeasonsRepository,
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<SeasonsParams, Resource<List<SeasonAnime>>>(dispatcherProvider) {
    override fun execute(params: SeasonsParams?) = params?.let {
        seasonsRepository.getSeasonUpcoming(it)
    } ?: flow {
        emit(Resource.Error(throwable = ParamsEmptyException()))
    }
}