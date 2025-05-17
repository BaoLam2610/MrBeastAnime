package com.lambao.mrbeast.presentation.ui.common.view_model.query_params

import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class QueryParamsViewModel @Inject constructor(
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider), QueryParamsDelegate {

    private val _type = MutableStateFlow("")

    private val _filter = MutableStateFlow("")

    private val _rating = MutableStateFlow("")

    private val _unApproved = MutableStateFlow<Boolean?>(null)

    private val _continuing = MutableStateFlow<Boolean?>(null)

    private val _sfw = MutableStateFlow<Boolean?>(null)

    override fun setType(type: String) {
        _type.value = type
    }

    override fun setFilter(filter: String) {
        _filter.value = filter
    }

    override fun setRating(rating: String) {
        _rating.value = rating
    }

    override fun setUnApproved(unApproved: Boolean?) {
        _unApproved.value = unApproved
    }

    override fun setContinuing(continuing: Boolean?) {
        _continuing.value = continuing
    }

    override fun setSfw(sfw: Boolean?) {
        _sfw.value = sfw
    }

    override fun getType(): StateFlow<String> = _type

    override fun getFilter(): StateFlow<String> = _filter

    override fun getRating(): StateFlow<String> = _rating

    override fun getUnApproved(): StateFlow<Boolean?> = _unApproved

    override fun getContinuing(): StateFlow<Boolean?> = _continuing

    override fun getSfw(): StateFlow<Boolean?> = _sfw
}