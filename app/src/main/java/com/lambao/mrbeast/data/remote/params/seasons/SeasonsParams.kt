package com.lambao.mrbeast.data.remote.params.seasons

import com.lambao.mrbeast.data.remote.params.PagingRequest

data class SeasonsParams(
    val filter: String = "", // "tv" "movie" "ova" "special" "ona" "music"
    val unapproved: String = "", // Boolean
    val continuing: String = "", // Boolean
    val sfw: String = "",
    override val page: String?,
    override val limit: String?
) : PagingRequest(page, limit) {
    override fun toQueryMap(): Map<String, String?> {
        return super.toQueryMap() + mapOf(
            "filter" to filter,
            "unapproved" to unapproved,
            "continuing" to continuing,
            "sfw" to sfw
        )
    }
}