package com.lambao.mrbeast.presentation.ui.common.view_model.query_params.search

import com.lambao.mrbeast.data.remote.params.search.SearchParams
import com.lambao.mrbeast.presentation.ui.common.view_model.query_params.QueryParamsDelegate
import kotlinx.coroutines.flow.StateFlow

interface SearchParamsDelegate : QueryParamsDelegate {
    fun getSearchParams(): StateFlow<SearchParams>
    fun updateSearchParams(params: SearchParams)
    fun resetParams()
}