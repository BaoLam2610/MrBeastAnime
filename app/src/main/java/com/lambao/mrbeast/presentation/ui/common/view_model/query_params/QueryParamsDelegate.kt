package com.lambao.mrbeast.presentation.ui.common.view_model.query_params

import kotlinx.coroutines.flow.StateFlow

interface QueryParamsDelegate {
    fun setQ(q: String)
    fun setScore(score: Double?)
    fun setMinScore(minScore: Double?)
    fun setMaxScore(maxScore: Double?)
    fun setStatus(status: String)
    fun setGenres(genres: List<String>)
    fun setGenresExclude(genresExclude: List<String>)
    fun setOrderBy(orderBy: String)
    fun setSort(sort: String)
    fun setLetter(letter: String)
    fun setProducers(producers: List<String>)
    fun setStartDate(startDate: String)
    fun setEndDate(endDate: String)
    fun setType(type: String)
    fun setFilter(filter: String)
    fun setRating(rating: String)
    fun setUnApproved(unApproved: Boolean?)
    fun setContinuing(continuing: Boolean?)
    fun setSfw(sfw: Boolean?)

    fun getQ(): StateFlow<String>
    fun getScore(): StateFlow<Double?>
    fun getMinScore(): StateFlow<Double?>
    fun getMaxScore(): StateFlow<Double?>
    fun getStatus(): StateFlow<String>
    fun getGenres(): StateFlow<List<String>>
    fun getGenresExclude(): StateFlow<List<String>>
    fun getOrderBy(): StateFlow<String>
    fun getSort(): StateFlow<String>
    fun getLetter(): StateFlow<String>
    fun getProducers(): StateFlow<List<String>>
    fun getStartDate(): StateFlow<String>
    fun getEndDate(): StateFlow<String>
    fun getType(): StateFlow<String>
    fun getFilter(): StateFlow<String>
    fun getRating(): StateFlow<String>
    fun getUnApproved(): StateFlow<Boolean?>
    fun getContinuing(): StateFlow<Boolean?>
    fun getSfw(): StateFlow<Boolean?>
}