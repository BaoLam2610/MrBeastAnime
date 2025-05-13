package com.lambao.mrbeast.data.remote.params.watch

import com.lambao.mrbeast.data.remote.params.PagingRequest

class WatchParams(
    override val page: Int = 1
) : PagingRequest(page, null)