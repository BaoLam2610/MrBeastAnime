package com.lambao.mrbeast.presentation.ui.common.view_model.top.paging

import com.lambao.base.presentation.ui.viewmodel.paging.PagingDelegate
import com.lambao.mrbeast.domain.model.display.DisplayTopAnimeInfo
import kotlinx.coroutines.flow.StateFlow

interface TopAnimePagingDelegate : PagingDelegate {
    fun getTopAnimeList(): StateFlow<List<DisplayTopAnimeInfo>>
    fun fetchTopAnime()
}