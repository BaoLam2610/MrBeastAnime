package com.lambao.mrbeast.data.remote.params.top

import com.lambao.mrbeast.data.remote.params.PagingRequest

data class TopParams(
    val type: String = "",
    val filter: String = "",
    val rating: String = "",
    val sfw: Boolean? = null,
    override val page: Int,
    override val limit: Int
) : PagingRequest(page, limit) {
    override fun toQueryMap(): Map<String, String> {
        return super.toQueryMap() + mapOf(
            "type" to type,
            "filter" to filter,
            "rating" to rating,
            "sfw" to (sfw?.toString() ?: "")
        )
    }
}