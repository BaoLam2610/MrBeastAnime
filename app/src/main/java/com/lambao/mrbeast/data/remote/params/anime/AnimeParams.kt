package com.lambao.mrbeast.data.remote.params.anime

import com.lambao.mrbeast.data.remote.params.PagingRequest

class AnimeParams(
    val id: String,
    override val page: Int = 1
) : PagingRequest(page, null)