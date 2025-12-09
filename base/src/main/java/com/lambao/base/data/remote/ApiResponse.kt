package com.lambao.base.data.remote

import com.google.gson.annotations.SerializedName
import com.lambao.base.data.remote.paging.Pagination

data class ApiResponse<T>(
    @SerializedName("pagination") val pagination: Pagination? = null,
    @SerializedName("data") val data: T? = null,
    @SerializedName("status") val status: Int? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("messages") val messages: Map<String, List<String>>? = null,
    @SerializedName("error") val error: String? = null
)