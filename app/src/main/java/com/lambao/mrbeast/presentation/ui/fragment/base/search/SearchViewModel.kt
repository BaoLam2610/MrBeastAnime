package com.lambao.mrbeast.presentation.ui.fragment.base.search

import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.paging.BasePagingViewModel
import com.lambao.mrbeast.presentation.ui.common.view_model.data_handler.DataHandlerDelegate
import com.lambao.mrbeast.presentation.ui.common.view_model.data_handler.DataHandlerViewModel
import com.lambao.mrbeast.presentation.ui.common.view_model.query_params.search.SearchParamsDelegate
import com.lambao.mrbeast.presentation.ui.common.view_model.query_params.search.SearchParamsViewModel
import com.lambao.mrbeast.presentation.ui.common.view_model.search_input.SearchInputDelegate
import com.lambao.mrbeast.presentation.ui.common.view_model.search_input.SearchInputViewModel

abstract class SearchViewModel<T : Any>(
    dispatcherProvider: DispatcherProvider
) : BasePagingViewModel<T>(dispatcherProvider),
    SearchInputDelegate by SearchInputViewModel(dispatcherProvider),
    SearchParamsDelegate by SearchParamsViewModel(dispatcherProvider),
    DataHandlerDelegate by DataHandlerViewModel(dispatcherProvider)