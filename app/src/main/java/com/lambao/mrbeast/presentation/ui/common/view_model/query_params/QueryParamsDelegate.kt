package com.lambao.mrbeast.presentation.ui.common.view_model.query_params

import kotlinx.coroutines.flow.StateFlow

interface QueryParamsDelegate {
    fun setType(type: String)
    fun setFilter(filter: String)
    fun setRating(rating: String)
    fun setUnApproved(unApproved: Boolean?)
    fun setContinuing(continuing: Boolean?)
    fun setSfw(sfw: Boolean?)

    fun getType(): StateFlow<String>
    fun getFilter(): StateFlow<String>
    fun getRating(): StateFlow<String>
    fun getUnApproved(): StateFlow<Boolean?>
    fun getContinuing(): StateFlow<Boolean?>
    fun getSfw(): StateFlow<Boolean?>
}