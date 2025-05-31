package com.lambao.mrbeast.presentation.ui.common.view_model.query_params

import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
open class QueryParamsViewModel @Inject constructor(
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider), QueryParamsDelegate {

    private val _q = MutableStateFlow("")

    private val _score = MutableStateFlow<Double?>(null)

    private val _minScore = MutableStateFlow<Double?>(null)

    private val _maxScore = MutableStateFlow<Double?>(null)

    private val _status = MutableStateFlow("")

    private val _genres = MutableStateFlow<List<String>>(emptyList())

    private val _genresExclude = MutableStateFlow<List<String>>(emptyList())

    private val _orderBy = MutableStateFlow("")

    private val _sort = MutableStateFlow("")

    private val _letter = MutableStateFlow("")

    private val _producers = MutableStateFlow<List<String>>(emptyList())

    private val _startDate = MutableStateFlow("")

    private val _endDate = MutableStateFlow("")

    private val _type = MutableStateFlow("")

    private val _filter = MutableStateFlow("")

    private val _rating = MutableStateFlow("")

    private val _unApproved = MutableStateFlow<Boolean?>(null)

    private val _continuing = MutableStateFlow<Boolean?>(null)

    private val _sfw = MutableStateFlow<Boolean?>(null)

    override fun setQ(q: String) {
        _q.value = q
    }

    override fun setScore(score: Double?) {
        _score.value = score
    }

    override fun setMinScore(minScore: Double?) {
        _minScore.value = minScore
    }

    override fun setMaxScore(maxScore: Double?) {
        _maxScore.value = maxScore
    }

    override fun setStatus(status: String) {
        _status.value = status
    }

    override fun setGenres(genres: List<String>) {
        _genres.value = genres
    }

    override fun setGenresExclude(genresExclude: List<String>) {
        _genresExclude.value = genresExclude
    }

    override fun setOrderBy(orderBy: String) {
        _orderBy.value = orderBy
    }

    override fun setSort(sort: String) {
        _sort.value = sort
    }

    override fun setLetter(letter: String) {
        _letter.value = letter
    }

    override fun setProducers(producers: List<String>) {
        _producers.value = producers
    }

    override fun setStartDate(startDate: String) {
        _startDate.value = startDate
    }

    override fun setEndDate(endDate: String) {
        _endDate.value = endDate
    }

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

    override fun getQ(): StateFlow<String> = _q

    override fun getScore(): StateFlow<Double?> = _score

    override fun getMinScore(): StateFlow<Double?> = _minScore

    override fun getMaxScore(): StateFlow<Double?> = _maxScore

    override fun getStatus(): StateFlow<String> = _status

    override fun getGenres(): StateFlow<List<String>> = _genres

    override fun getGenresExclude(): StateFlow<List<String>> = _genresExclude

    override fun getOrderBy(): StateFlow<String> = _orderBy

    override fun getSort(): StateFlow<String> = _sort

    override fun getLetter(): StateFlow<String> = _letter

    override fun getProducers(): StateFlow<List<String>> = _producers

    override fun getStartDate(): StateFlow<String> = _startDate

    override fun getEndDate(): StateFlow<String> = _endDate

    override fun getType(): StateFlow<String> = _type

    override fun getFilter(): StateFlow<String> = _filter

    override fun getRating(): StateFlow<String> = _rating

    override fun getUnApproved(): StateFlow<Boolean?> = _unApproved

    override fun getContinuing(): StateFlow<Boolean?> = _continuing

    override fun getSfw(): StateFlow<Boolean?> = _sfw
}