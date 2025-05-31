package com.lambao.mrbeast.data.remote.params.anime

import com.lambao.mrbeast.data.remote.params.PagingRequest

data class AnimeReviewParams(
    val id: String,
    val preliminary: Boolean? = null,
    val spoiler: Boolean? = null,
    override val page: Int = 1
) : PagingRequest(page, null) {
    override fun toQueryMap(): Map<String, String> {
        return super.toQueryMap() + mapOf(
            "preliminary" to (preliminary?.toString() ?: ""),
            "spoiler" to (spoiler?.toString() ?: "")
        )
    }
}