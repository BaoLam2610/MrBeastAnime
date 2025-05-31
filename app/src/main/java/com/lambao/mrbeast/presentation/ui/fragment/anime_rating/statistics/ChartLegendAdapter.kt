package com.lambao.mrbeast.presentation.ui.fragment.anime_rating.statistics

import com.lambao.base.presentation.ui.recycler_view.BaseRecyclerAdapter
import com.lambao.mrbeast.domain.model.chart.ChartLegend
import com.lambao.mrbeast_anime.R
import com.lambao.mrbeast_anime.databinding.ItemChartLegendBinding

class ChartLegendAdapter : BaseRecyclerAdapter<ChartLegend, ItemChartLegendBinding>() {
    override fun getLayoutId(viewType: Int) = R.layout.item_chart_legend

    override fun bind(binding: ItemChartLegendBinding, item: ChartLegend, position: Int) {
        binding.item = item
    }
}