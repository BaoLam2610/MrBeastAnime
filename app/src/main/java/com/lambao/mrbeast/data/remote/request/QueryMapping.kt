package com.lambao.mrbeast.data.remote.request

interface QueryMapping {
    fun toQueryMap(): Map<String, String?>
}