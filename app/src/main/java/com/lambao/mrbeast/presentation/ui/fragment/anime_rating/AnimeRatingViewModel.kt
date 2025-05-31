package com.lambao.mrbeast.presentation.ui.fragment.anime_rating

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.BaseViewModel
import com.lambao.mrbeast.domain.model.screen.TabLayoutScreenType
import com.lambao.mrbeast.presentation.ui.common.view_model.anime.AnimeDelegate
import com.lambao.mrbeast.presentation.ui.common.view_model.anime.AnimeViewModel
import com.lambao.mrbeast.presentation.ui.fragment.anime_rating.reviews.AnimeReviewsFragment
import com.lambao.mrbeast.presentation.ui.fragment.anime_rating.statistics.AnimeStatisticsFragment
import com.lambao.mrbeast_anime.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AnimeRatingViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider),
    AnimeDelegate by AnimeViewModel(dispatcherProvider) {

    private val _screenTypes = getAnimeId().map {
        if (it.isEmpty()) return@map emptyList()
        return@map listOf(
            TabLayoutScreenType(
                title = context.getString(R.string.review),
                fragment = AnimeReviewsFragment.newInstance(it)
            ),
            TabLayoutScreenType(
                title = context.getString(R.string.statistic),
                fragment = AnimeStatisticsFragment.newInstance(it)
            )
        )
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val screenTypes get() = _screenTypes

    private val _fragments = _screenTypes.map {
        it.map { item -> item.fragment }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val fragments get() = _fragments
}