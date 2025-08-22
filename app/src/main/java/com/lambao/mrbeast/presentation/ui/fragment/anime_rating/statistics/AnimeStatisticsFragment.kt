package com.lambao.mrbeast.presentation.ui.fragment.anime_rating.statistics

import android.graphics.Color
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.lambao.presentation.extension.observeLatest
import com.lambao.base.presentation.ui.fragment.BaseVMFragment
import com.lambao.base.presentation.ui.view.recycler_view.spacing
import com.lambao.mrbeast.domain.model.chart.OverallChart
import com.lambao.mrbeast.domain.model.chart.ScoreChart
import com.lambao.mrbeast.utils.Constants
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.FragmentAnimeStatisticsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class AnimeStatisticsFragment :
    BaseVMFragment<FragmentAnimeStatisticsBinding, AnimeStatisticsViewModel>() {

    companion object {
        fun newInstance(id: String?): AnimeStatisticsFragment {
            val args = Bundle().apply {
                putString(Constants.Bundle.ID, id)
            }

            val fragment = AnimeStatisticsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val argId by lazy {
        arguments?.getString(Constants.Bundle.ID)
    }

    private val pieChartLegend by lazy {
        ChartLegendAdapter()
    }

    override fun getLayoutResId() = R.layout.fragment_anime_statistics

    override fun getViewModelClass() = AnimeStatisticsViewModel::class.java

    override fun onViewReady(savedInstanceState: Bundle?) {
        binding.rvPieLegend.adapter = pieChartLegend
        binding.rvPieLegend.spacing {
            top = 16
            bottom = 16
        }
    }

    override fun initObserve() {
        binding.viewModel = viewModel

        observeLatest(viewModel.pieChartLegend) {
            pieChartLegend.submitList(it)
        }

        observeLatest(viewModel.overallChart) {
            if (it == null) return@observeLatest
            setupPieChart(it)
        }

        observeLatest(viewModel.scoreChart) {
            if (it == null) return@observeLatest
            setupBarChart(it)
        }

        viewModel.setAnimeId(argId ?: "")
        viewModel.fetchAnimeStatistics()
    }

    private fun setupPieChart(overallChart: OverallChart) {
        val entries = listOf(
            PieEntry(overallChart.watching, "Watching"),
            PieEntry(overallChart.completed, "Completed"),
            PieEntry(overallChart.onHold, "On Hold"),
            PieEntry(overallChart.dropped, "Dropped"),
            PieEntry(overallChart.planToWatch, "Plan to Watch")
        )

        val colors = viewModel.pieChartColors.value.map {
            ContextCompat.getColor(requireContext(), it)
        }

        binding.pieChart.apply {
            description.isEnabled = true
            legend.isEnabled = false
            setUsePercentValues(false)
            setDrawEntryLabels(false)
            setDrawRoundedSlices(false)
            setEntryLabelColor(Color.WHITE)
            setEntryLabelTextSize(12f)
            val dataSet = PieDataSet(entries, "Anime overall")
            dataSet.colors = colors

            val data = PieData(dataSet)
            data.setDrawValues(false)
            isDrawHoleEnabled = false
            isHighlightPerTapEnabled = false
            setTouchEnabled(false)
            offsetLeftAndRight(0)
            offsetTopAndBottom(0)

            this.data = data
            invalidate()
            animateY(500)
        }
    }

    private fun setupBarChart(scores: List<ScoreChart>) {
        // Prepare data entries
        val entries = mutableListOf<BarEntry>()
        val labels = mutableListOf<String>()

        scores.forEachIndexed { index, score ->
            entries.add(BarEntry(index.toFloat(), score.votes.toFloat()))
            labels.add("${score.score}⭐\uFE0F")
        }

        // Create dataset with gradient effect
        val dataSet = BarDataSet(entries, "Score Distribution").apply {
            // Beautiful gradient colors from red to green
            colors = viewModel.barChartColors.value.map {
                ContextCompat.getColor(requireContext(), it)
            }

            // Styling
            valueTextSize = 10f
            valueTextColor = ContextCompat.getColor(requireContext(), com.lambao.base.R.color.text_secondary)
            setDrawValues(true)
        }

        // Custom value formatter to show votes with K suffix
        dataSet.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return when {
                    value >= 1000 -> "${(value / 1000).roundToInt()}K"
                    else -> value.roundToInt().toString()
                }
            }
        }

        // Create bar data
        val barData = BarData(dataSet).apply {
            barWidth = 0.8f
        }

        // Configure chart appearance
        binding.barChart.apply {
            data = barData
            description.isEnabled = false

            // X-axis configuration for horizontal scrolling
            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                valueFormatter = IndexAxisValueFormatter(labels)
                granularity = 1f
                textSize = 12f
                textColor = ContextCompat.getColor(requireContext(), com.lambao.base.R.color.text_secondary)
                setDrawGridLines(false)
                gridColor = ContextCompat.getColor(requireContext(), com.lambao.base.R.color.background_disabled)
                gridLineWidth = 0.5f
                setDrawAxisLine(false)
                axisLineColor = ContextCompat.getColor(requireContext(), com.lambao.base.R.color.background_disabled)

                // Configure for scrolling - show all labels
                axisMinimum = -0.5f
                axisMaximum = labels.size - 0.5f
                labelCount = labels.size
                setAvoidFirstLastClipping(false) // Allow clipping for scrolling

                // Better label spacing for horizontal scroll
                labelRotationAngle = 0f
                setCenterAxisLabels(false)
            }

            // Left Y-axis configuration
            axisLeft.apply {
                textSize = 12f
                textColor = ContextCompat.getColor(requireContext(), com.lambao.base.R.color.text_secondary)
                setDrawGridLines(true)
                gridColor = ContextCompat.getColor(requireContext(), com.lambao.base.R.color.background_disabled)
                gridLineWidth = 0.5f
                setDrawAxisLine(true)
                axisLineColor = ContextCompat.getColor(requireContext(), com.lambao.base.R.color.background_disabled)

                // Remove space at bottom - start from 0
                axisMinimum = 0f
                setDrawZeroLine(true)
                zeroLineColor = Color.TRANSPARENT
                zeroLineWidth = 0f

                // Custom value formatter for Y-axis
                valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        return when {
                            value >= 1000000 -> "${(value / 1000000).roundToInt()}M"
                            value >= 1000 -> "${(value / 1000).roundToInt()}K"
                            else -> value.roundToInt().toString()
                        }
                    }
                }
            }

            // Right Y-axis (disable)
            axisRight.isEnabled = false

            // Legend
            legend.isEnabled = false

            // Interaction - Enable horizontal scrolling
            setScaleEnabled(false) // Disable zoom
            setPinchZoom(false)
            isDoubleTapToZoomEnabled = false

            // Enable horizontal scrolling/dragging
            isDragEnabled = true
            isDragDecelerationEnabled = true
            dragDecelerationFrictionCoef = 0.9f

            // Set visible range to show 5-6 bars at a time for better UX
            setVisibleXRangeMaximum(6f)
            setVisibleXRangeMinimum(3f)

            // Animation
            animateY(1200, com.github.mikephil.charting.animation.Easing.EaseInOutCubic)

            // Styling
            setDrawBorders(false)
            setDrawGridBackground(false)

            // Extra margins for better scrolling experience
            setExtraOffsets(15f, 20f, 15f, 10f)

            // Move to show lower scores first (optional - you can remove this)
            moveViewToX((scores.size - 1).toFloat())

            invalidate() // Refresh
        }
    }
}