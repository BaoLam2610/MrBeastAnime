package com.lambao.mrbeast.presentation.ui.common.view_model.query_params.search

import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.mrbeast.data.remote.params.search.SearchParams
import com.lambao.mrbeast.presentation.ui.common.view_model.query_params.QueryParamsViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SearchParamsViewModel @Inject constructor(
    dispatcherProvider: DispatcherProvider
) : QueryParamsViewModel(dispatcherProvider), SearchParamsDelegate {

    private val _defaultParams = MutableStateFlow(SearchParams())

    private val _params = MutableStateFlow(_defaultParams.value.copy())

    override fun getSearchParams(): StateFlow<SearchParams> = _params

    override fun updateSearchParams(params: SearchParams) {
        _params.value = params
    }

    override fun resetParams() {
        _params.value = _defaultParams.value.copy()
    }
}