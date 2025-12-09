package com.lambao.base.data.remote.paging

import com.google.gson.annotations.SerializedName

data class Pagination(
    @SerializedName("last_visible_page") val lastVisiblePage: Int? = null,
    @SerializedName("has_next_page") override val hasNextPage: Boolean? = null,
    @SerializedName("current_page") override val currentPage: Int? = null,
    @SerializedName("items") val items: PageItem? = null
) : Paging {
    override val totalPages: Int? get() = lastVisiblePage
    override val totalItems: Int? get() = items?.total
    override val perPage: Int? get() = items?.perPage
}

interface Paging {
    val hasNextPage: Boolean?
    val currentPage: Int?
    val totalPages: Int?
    val totalItems: Int?
    val perPage: Int?
}