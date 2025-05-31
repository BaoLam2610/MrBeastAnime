package com.lambao.mrbeast.data.local.entity

import androidx.room.Ignore

open class BaseEntity(
    @Ignore open val malId: Int
)