package com.lambao.mrbeast.domain.model.chart

data class OverallChart(
    val watching: Float,
    val completed: Float,
    val onHold: Float,
    val dropped: Float,
    val planToWatch: Float,
    val total: Float
)