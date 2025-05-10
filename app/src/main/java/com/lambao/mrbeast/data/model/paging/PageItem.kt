package com.lambao.mrbeast.data.model.paging

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PageItem(
    @Expose @SerializedName("count") val count: Int? = null,
    @Expose @SerializedName("total") val total: Int? = null,
    @Expose @SerializedName("per_page") val perPage: Int? = null
)