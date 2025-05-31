package com.lambao.mrbeast.presentation.ui.common.view_model.anime_filter.data

import android.content.Context
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.BaseViewModel
import com.lambao.mrbeast.domain.model.display.selector.OrderBySelector
import com.lambao.mrbeast.domain.model.display.selector.RatingSelector
import com.lambao.mrbeast.domain.model.display.selector.SortSelector
import com.lambao.mrbeast.domain.model.display.selector.StatusSelector
import com.lambao.mrbeast.domain.model.display.selector.TypeSelector
import com.lambao.mrbeast.domain.model.display.selector.UnapprovedSelector
import com.lambao.mrbeast_anime.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class AnimeSelectorDataViewModel @Inject constructor(
    @ApplicationContext context: Context,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider), AnimeSelectorDataDelegate {

    private val _unapprovedSelectors = MutableStateFlow(
        listOf(
            UnapprovedSelector(
                type = UnapprovedSelector.Type.YES,
                displayText = context.getString(R.string.yes)
            ),
            UnapprovedSelector(
                type = UnapprovedSelector.Type.NO,
                displayText = context.getString(R.string.no)
            ),
            UnapprovedSelector(
                type = UnapprovedSelector.Type.DEFAULT,
                displayText = context.getString(R.string.text_default)
            )
        )
    )

    private val _typeSelectors = MutableStateFlow(
        listOf(
            TypeSelector(
                type = TypeSelector.Type.TV
            ),
            TypeSelector(
                type = TypeSelector.Type.MOVIE
            ),
            TypeSelector(
                type = TypeSelector.Type.OVA
            ),
            TypeSelector(
                type = TypeSelector.Type.SPECIAL
            ),
            TypeSelector(
                type = TypeSelector.Type.ONA
            ),
            TypeSelector(
                type = TypeSelector.Type.MUSIC
            ),
            TypeSelector(
                type = TypeSelector.Type.COMEDY
            ),
            TypeSelector(
                type = TypeSelector.Type.PERSONAL_VIDEO
            ),
            TypeSelector(
                type = TypeSelector.Type.TV_SPECIAL
            ),
            TypeSelector(
                type = TypeSelector.Type.DEFAULT,
                displayText = context.getString(R.string.text_default)
            )
        )
    )

    private val _statusSelectors = MutableStateFlow(
        listOf(
            StatusSelector(
                type = StatusSelector.Type.AIRING,
                displayText = context.getString(R.string.airing)
            ),
            StatusSelector(
                type = StatusSelector.Type.COMPLETE,
                displayText = context.getString(R.string.completed)
            ),
            StatusSelector(
                type = StatusSelector.Type.UPCOMING,
                displayText = context.getString(R.string.upcoming)
            ),
            StatusSelector(
                type = StatusSelector.Type.DEFAULT,
                displayText = context.getString(R.string.text_default)
            )
        )
    )

    private val _ratingSelectors = MutableStateFlow(
        listOf(
            RatingSelector(
                type = RatingSelector.Type.G,
                displayText = context.getString(R.string.all_ages)
            ),
            RatingSelector(
                type = RatingSelector.Type.PG,
                displayText = context.getString(R.string.children)
            ),
            RatingSelector(
                type = RatingSelector.Type.PG13,
                displayText = context.getString(R.string.teens_13_or_older)
            ),
            RatingSelector(
                type = RatingSelector.Type.R17,
                displayText = context.getString(R.string.violence_profanity)
            ),
            RatingSelector(
                type = RatingSelector.Type.R,
                displayText = context.getString(R.string.mild_nudity)
            ),
            RatingSelector(
                type = RatingSelector.Type.RX,
                displayText = context.getString(R.string.hentai)
            ),
            RatingSelector(
                type = RatingSelector.Type.DEFAULT,
                displayText = context.getString(R.string.text_default)
            )
        )
    )

    private val _orderBySelectors = MutableStateFlow(
        listOf(
            OrderBySelector(
                type = OrderBySelector.Type.MAL_ID,
                displayText = context.getString(R.string.by_id)
            ),
            OrderBySelector(
                type = OrderBySelector.Type.TITLE,
                displayText = context.getString(R.string.title)
            ),
            OrderBySelector(
                type = OrderBySelector.Type.START_DATE,
                displayText = context.getString(R.string.start_date)
            ),
            OrderBySelector(
                type = OrderBySelector.Type.END_DATE,
                displayText = context.getString(R.string.end_date)
            ),
            OrderBySelector(
                type = OrderBySelector.Type.EPISODES,
                displayText = context.getString(R.string.episode)
            ),
            OrderBySelector(
                type = OrderBySelector.Type.SCORE,
                displayText = context.getString(R.string.score)
            ),
            OrderBySelector(
                type = OrderBySelector.Type.SCORED_BY,
                displayText = context.getString(R.string.score_by)
            ),
            OrderBySelector(
                type = OrderBySelector.Type.RANK,
                displayText = context.getString(R.string.rank)
            ),
            OrderBySelector(
                type = OrderBySelector.Type.POPULARITY,
                displayText = context.getString(R.string.popularity)
            ),
            OrderBySelector(
                type = OrderBySelector.Type.MEMBERS,
                displayText = context.getString(R.string.members_follow)
            ),
            OrderBySelector(
                type = OrderBySelector.Type.FAVORITES,
                displayText = context.getString(R.string.favorites)
            ),
            OrderBySelector(
                type = OrderBySelector.Type.DEFAULT,
                displayText = context.getString(R.string.text_default)
            )
        )
    )

    private val _sortSelectors = MutableStateFlow(
        listOf(
            SortSelector(
                type = SortSelector.Type.ASC,
                displayText = context.getString(R.string.asc)
            ),
            SortSelector(
                type = SortSelector.Type.DESC,
                displayText = context.getString(R.string.desc)
            ),
            SortSelector(
                type = SortSelector.Type.DEFAULT,
                displayText = context.getString(R.string.text_default)
            )
        )
    )

    private val _unapprovedSelector = MutableStateFlow(_unapprovedSelectors.value.last())

    private val _typeSelector = MutableStateFlow(_typeSelectors.value.last())

    private val _statusSelector = MutableStateFlow(_statusSelectors.value.last())

    private val _ratingSelector = MutableStateFlow(_ratingSelectors.value.last())

    private val _orderBySelector = MutableStateFlow(_orderBySelectors.value.last())

    private val _sortSelector = MutableStateFlow(_sortSelectors.value.last())

    override fun getAnimeUnapprovedSelector() = _unapprovedSelector

    override fun getAnimeTypeSelector() = _typeSelector

    override fun getAnimeStatusSelector() = _statusSelector

    override fun getAnimeRatingSelector() = _ratingSelector

    override fun getAnimeOrderBySelector() = _orderBySelector

    override fun getAnimeSortSelector() = _sortSelector

    override fun getAnimeUnapprovedSelectors() = _unapprovedSelectors

    override fun getAnimeTypeSelectors() = _typeSelectors

    override fun getAnimeStatusSelectors() = _statusSelectors

    override fun getAnimeRatingSelectors() = _ratingSelectors

    override fun getAnimeOrderBySelectors() = _orderBySelectors

    override fun getAnimeSortSelectors() = _sortSelectors
}