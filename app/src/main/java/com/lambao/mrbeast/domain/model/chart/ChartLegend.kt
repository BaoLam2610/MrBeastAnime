package com.lambao.mrbeast.domain.model.chart

data class ChartLegend(
    val backgroundId: Int,
    val title: String,
    val count: Int,
    val percent: Float
) {
    override fun toString(): String {
        return try {
//            val formattedPercent = String.format(Locale.US, "%.2f", percent)
            "$title ($count)"
        } catch (e: Exception) {
            title
        }
    }
}
