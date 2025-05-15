package com.lambao.mrbeast.presentation.ui.common.view_model.watch

import com.lambao.base.data.Resource
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.BaseViewModel
import com.lambao.mrbeast.data.remote.params.watch.WatchParams
import com.lambao.mrbeast.domain.model.display.DisplayWatchAnimeInfo
import com.lambao.mrbeast.domain.usecase.watch.WatchAnimeUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class WatchAnimeViewModel<T : DisplayWatchAnimeInfo>(
    private val watchAnimeUseCase: WatchAnimeUseCase<T>,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider), WatchAnimeDelegate<T> {

    private val _watchAnimeList = MutableStateFlow<List<DisplayWatchAnimeInfo>>(emptyList())

    override fun setWatchAnimeList(data: List<DisplayWatchAnimeInfo>) {
        launch {
            _watchAnimeList.emit(data)
        }
    }

    override fun getWatchAnimeList(): StateFlow<List<DisplayWatchAnimeInfo>> = _watchAnimeList

    override fun getWatchAnimeUseCaseFlow(params: WatchParams): Flow<Resource<List<T>>> {
        return watchAnimeUseCase.invoke(params)
    }

    override fun fetchWatchAnime(params: WatchParams) {
        handleData(watchAnimeUseCase.invoke(params)) {
            _watchAnimeList.emit(it)
        }
    }
}