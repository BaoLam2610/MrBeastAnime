package com.lambao.mrbeast.presentation.ui.fragment.base.anime_list

import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.paging.RemotePagingViewModel
import com.lambao.mrbeast.presentation.ui.common.view_model.empty_data.EmptyDataDelegate
import com.lambao.mrbeast.presentation.ui.common.view_model.empty_data.EmptyDataViewModel
import com.lambao.mrbeast.presentation.ui.common.view_model.query_params.QueryParamsDelegate
import com.lambao.mrbeast.presentation.ui.common.view_model.query_params.QueryParamsViewModel

abstract class AnimeListViewModel<T : Any>(
    dispatcherProvider: DispatcherProvider
) : RemotePagingViewModel<T>(dispatcherProvider),
    QueryParamsDelegate by QueryParamsViewModel(dispatcherProvider),
    EmptyDataDelegate by EmptyDataViewModel(dispatcherProvider)