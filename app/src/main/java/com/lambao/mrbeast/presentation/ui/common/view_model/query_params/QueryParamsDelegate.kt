package com.lambao.mrbeast.presentation.ui.common.view_model.query_params

import kotlinx.coroutines.flow.StateFlow

interface QueryParamsDelegate {
    fun setType(type: String)
    fun setFilter(filter: String)
    fun setRating(rating: String)
    fun getType(): StateFlow<String>
    fun getFilter(): StateFlow<String>
    fun getRating(): StateFlow<String>
}