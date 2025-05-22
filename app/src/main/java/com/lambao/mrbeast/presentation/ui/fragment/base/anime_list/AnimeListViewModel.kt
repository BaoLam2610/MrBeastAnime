package com.lambao.mrbeast.presentation.ui.fragment.base.anime_list

import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.paging.BasePagingViewModel
import com.lambao.mrbeast.presentation.ui.common.view_model.data_handler.DataHandlerDelegate
import com.lambao.mrbeast.presentation.ui.common.view_model.data_handler.DataHandlerViewModel
import com.lambao.mrbeast.presentation.ui.common.view_model.query_params.QueryParamsDelegate
import com.lambao.mrbeast.presentation.ui.common.view_model.query_params.QueryParamsViewModel

abstract class AnimeListViewModel<T : Any>(
    dispatcherProvider: DispatcherProvider
) : BasePagingViewModel<T>(dispatcherProvider),
    QueryParamsDelegate by QueryParamsViewModel(dispatcherProvider),
    DataHandlerDelegate by DataHandlerViewModel(dispatcherProvider)