package com.lambao.base.data.remote.paging

import com.google.gson.annotations.SerializedName

data class Pagination(
    @SerializedName("last_visible_page") val lastVisiblePage: Int? = null,
    @SerializedName("has_next_page") val hasNextPage: Boolean? = null,
    @SerializedName("current_page") val currentPage: Int? = null,
    @SerializedName("items") val items: PageItem? = null
)