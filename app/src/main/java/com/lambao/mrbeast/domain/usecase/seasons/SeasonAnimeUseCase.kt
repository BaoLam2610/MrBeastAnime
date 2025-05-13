package com.lambao.mrbeast.domain.usecase.seasons

import com.lambao.base.data.Resource
import com.lambao.base.domain.FlowUseCase
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.mrbeast.data.model.seasons.SeasonAnime
import com.lambao.mrbeast.data.remote.params.seasons.SeasonsParams

abstract class SeasonAnimeUseCase(
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<SeasonsParams, Resource<List<SeasonAnime>>>(dispatcherProvider)