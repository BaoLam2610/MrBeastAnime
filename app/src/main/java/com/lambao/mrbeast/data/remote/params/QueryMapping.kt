package com.lambao.mrbeast.data.remote.params

interface QueryMapping {
    fun toQueryMap(): Map<String, String?>
}