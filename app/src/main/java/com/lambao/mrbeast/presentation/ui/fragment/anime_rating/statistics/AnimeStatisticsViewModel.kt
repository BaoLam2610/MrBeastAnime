package com.lambao.mrbeast.presentation.ui.fragment.anime_rating.statistics

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.lambao.base.presentation.handler.dispatcher.DispatcherProvider
import com.lambao.base.presentation.ui.viewmodel.BaseViewModel
import com.lambao.mrbeast.data.model.anime.AnimeStatistic
import com.lambao.mrbeast.data.remote.params.anime.AnimeParams
import com.lambao.mrbeast.domain.model.chart.ChartLegend
import com.lambao.mrbeast.domain.model.chart.OverallChart
import com.lambao.mrbeast.domain.model.chart.ScoreChart
import com.lambao.mrbeast.domain.usecase.anime.GetAnimeStatisticsUseCase
import com.lambao.mrbeast.presentation.ui.common.view_model.anime.AnimeDelegate
import com.lambao.mrbeast.presentation.ui.common.view_model.anime.AnimeViewModel
import com.lambao.mrbeast_anime.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AnimeStatisticsViewModel @Inject constructor(
    private val getAnimeStatisticsUseCase: GetAnimeStatisticsUseCase,
    @ApplicationContext private val context: Context,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider),
    AnimeDelegate by AnimeViewModel(dispatcherProvider) {

    private val _pieChartColors = MutableStateFlow(
        listOf(
            com.lambao.base.R.color.green_300,
            com.lambao.base.R.color.blue_300,
            com.lambao.base.R.color.amber_300,
            com.lambao.base.R.color.red_300,
            com.lambao.base.R.color.purple_300
        )
    )
    val pieChartColors = _pieChartColors.asStateFlow()

    private val _barChartColors = MutableStateFlow(
        listOf(
            com.lambao.base.R.color.red_400,
            com.lambao.base.R.color.red_300,
            com.lambao.base.R.color.orange_300,
            com.lambao.base.R.color.orange_200,
            com.lambao.base.R.color.orange_100,
            com.lambao.base.R.color.green_200,
            com.lambao.base.R.color.green_300,
            com.lambao.base.R.color.green_400,
            com.lambao.base.R.color.green_500,
            com.lambao.base.R.color.green_600,
        )
    )
    val barChartColors = _barChartColors.asStateFlow()

    private val _animeStatistic = MutableStateFlow<AnimeStatistic?>(null)

    private val _overallChart = _animeStatistic.map {
        OverallChart(
            it?.watching?.toFloat() ?: 0f,
            it?.completed?.toFloat() ?: 0f,
            it?.onHold?.toFloat() ?: 0f,
            it?.dropped?.toFloat() ?: 0f,
            it?.planToWatch?.toFloat() ?: 0f,
            it?.total?.toFloat() ?: 0f
        )
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)
    val overallChart get() = _overallChart

    private val _scoreChart = _animeStatistic.map {
        it?.scores?.map { item ->
            ScoreChart(
                item.score ?: 0,
                item.votes ?: 0,
                item.percentage?.toFloat() ?: 0f
            )
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)
    val scoreChart get() = _scoreChart

    private val _pieChartLegend = combine(
        _overallChart,
        _pieChartColors
    ) { overallChart, colors ->
        if (overallChart == null) return@combine emptyList()
        listOf(
            ChartLegend(
                colors[0],
                context.getString(R.string.watching),
                overallChart.watching.toInt(),
                (overallChart.watching / overallChart.total) * 100
            ),
            ChartLegend(
                colors[1],
                context.getString(R.string.watched),
                overallChart.completed.toInt(),
                (overallChart.completed / overallChart.total) * 100
            ),
            ChartLegend(
                colors[2],
                context.getString(R.string.on_hold_watch),
                overallChart.onHold.toInt(),
                (overallChart.onHold / overallChart.total) * 100
            ),
            ChartLegend(
                colors[3],
                context.getString(R.string.dropped_watch),
                overallChart.dropped.toInt(),
                (overallChart.dropped / overallChart.total) * 100
            ),
            ChartLegend(
                colors[4],
                context.getString(R.string.plan_to_watch),
                overallChart.planToWatch.toInt(),
                (overallChart.planToWatch / overallChart.total) * 100
            )
        ).sortedByDescending { it.count }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val pieChartLegend get() = _pieChartLegend

    fun fetchAnimeStatistics() {
        handleData(
            getAnimeStatisticsUseCase.invoke(AnimeParams(id = getAnimeId().value)),
        ) {
            _animeStatistic.emit(it)
        }
    }
}