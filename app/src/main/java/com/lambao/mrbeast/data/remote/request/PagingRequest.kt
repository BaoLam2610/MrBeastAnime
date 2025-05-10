package com.lambao.mrbeast.data.remote.request

abstract class PagingRequest(
    open val page: String?,
    open val limit: String?,
) : QueryMapping {
    override fun toQueryMap() = mapOf(
        "page" to page,
        "limit" to limit
    )
}
