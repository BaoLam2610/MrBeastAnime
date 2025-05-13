package com.lambao.mrbeast.data.remote.params

abstract class PagingRequest(
    open val page: Int?,
    open val limit: Int?,
) : QueryMapping {
    override fun toQueryMap() = buildMap {
        if (page != null) {
            put("page", page.toString())
        }
        if (limit != null) {
            put("limit", limit.toString())
        }
    }
}
