package com.lambao.mrbeast.data.remote.params.top_anime

import com.lambao.mrbeast.data.remote.params.PagingRequest

data class TopAnimeParams(
    val type: String = "",
    val filter: String = "",
    val rating: String = "",
    val sfw: String = "",
    override val page: String?,
    override val limit: String?
) : PagingRequest(page, limit) {
    override fun toQueryMap(): Map<String, String?> {
        return super.toQueryMap() + mapOf(
            "type" to type,
            "filter" to filter,
            "rating" to rating,
            "sfw" to sfw
        )
    }
}