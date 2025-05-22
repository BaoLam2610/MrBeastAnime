package com.lambao.mrbeast.data.remote.params.anime

import com.lambao.mrbeast.data.remote.params.PagingRequest

data class AnimeParams(
    val id: String,
    override val page: Int = 1,
    override val limit: Int = 10
) : PagingRequest(page, limit)