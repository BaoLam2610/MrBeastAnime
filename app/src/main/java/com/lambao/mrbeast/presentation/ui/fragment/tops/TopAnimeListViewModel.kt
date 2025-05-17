package com.lambao.mrbeast.presentation.ui.fragment.tops

import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.mrbeast.domain.usecase.GetTopAnimeUseCase
import com.lambao.mrbeast.presentation.ui.common.view_model.top.TopAnimeDelegate
import com.lambao.mrbeast.presentation.ui.common.view_model.top.TopAnimeViewModel
import com.lambao.mrbeast.presentation.ui.fragment.base.anime_list.AnimeListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopAnimeListViewModel @Inject constructor(
    getTopAnimeUseCase: GetTopAnimeUseCase,
    dispatcherProvider: DispatcherProvider
) : AnimeListViewModel(dispatcherProvider),
    TopAnimeDelegate by TopAnimeViewModel(getTopAnimeUseCase, dispatcherProvider) {
}