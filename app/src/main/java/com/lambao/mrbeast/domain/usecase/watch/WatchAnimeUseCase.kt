package com.lambao.mrbeast.domain.usecase.watch

import com.lambao.base.data.Resource
import com.lambao.base.domain.FlowUseCase
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.mrbeast.data.remote.params.watch.WatchParams
import com.lambao.mrbeast.domain.model.display.DisplayWatchAnimeInfo

abstract class WatchAnimeUseCase<T : DisplayWatchAnimeInfo>(
    dispatcherProvider: DispatcherProvider
) : FlowUseCase<WatchParams, Resource<List<T>>>(dispatcherProvider)