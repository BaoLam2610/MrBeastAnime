package com.lambao.mrbeast.presentation.ui.fragment.anime_rating.detail

import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.BaseViewModel
import com.lambao.mrbeast.data.model.anime.AnimeReview
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AnimeReviewDetailViewModel @Inject constructor(
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {

    private val _review = MutableStateFlow<AnimeReview?>(null)
    val review = _review.asStateFlow()

    fun setAnimeReview(review: AnimeReview) {
        _review.value = review
    }
}