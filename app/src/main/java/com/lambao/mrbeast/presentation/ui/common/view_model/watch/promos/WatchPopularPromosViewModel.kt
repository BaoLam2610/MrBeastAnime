package com.lambao.mrbeast.presentation.ui.common.view_model.watch.promos

import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.mrbeast.data.model.watch.WatchPromo
import com.lambao.mrbeast.data.remote.params.watch.WatchParams
import com.lambao.mrbeast.domain.model.display.DisplayWatchAnimeInfo
import com.lambao.mrbeast.domain.usecase.watch.GetWatchPopularPromosUseCase
import com.lambao.mrbeast.presentation.ui.common.view_model.watch.WatchAnimeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WatchPopularPromosViewModel @Inject constructor(
    getWatchPopularPromosUseCase: GetWatchPopularPromosUseCase,
    dispatcherProvider: DispatcherProvider
) : WatchAnimeViewModel<WatchPromo>(getWatchPopularPromosUseCase, dispatcherProvider),
    WatchPopularPromosDelegate {
    override fun setWatchPromoList(data: List<DisplayWatchAnimeInfo>) {
        setWatchAnimeList(data)
    }

    override fun getWatchPromoList() = getWatchAnimeList()

    override fun getWatchPromoUseCaseFlow(params: WatchParams) =
        getWatchAnimeUseCaseFlow(params)

    override fun fetchWatchPromo(params: WatchParams) {
        fetchWatchAnime(params)
    }
}