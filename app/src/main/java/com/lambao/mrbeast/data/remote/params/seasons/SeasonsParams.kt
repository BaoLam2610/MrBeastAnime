package com.lambao.mrbeast.data.remote.params.seasons

import com.lambao.mrbeast.data.remote.params.PagingRequest

data class SeasonsParams(
    val filter: String = "", // "tv" "movie" "ova" "special" "ona" "music"
    val unapproved: Boolean? = null,
    val continuing: Boolean? = null,
    val sfw: Boolean? = null,
    override val page: Int,
    override val limit: Int
) : PagingRequest(page, limit) {
    override fun toQueryMap(): Map<String, String> {
        return super.toQueryMap() + mapOf(
            "filter" to filter,
            "unapproved" to (unapproved?.toString() ?: ""),
            "continuing" to (continuing?.toString() ?: ""),
            "sfw" to (sfw?.toString() ?: "")
        )
    }
}