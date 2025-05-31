package com.lambao.mrbeast.data.local.entity.genres

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lambao.mrbeast.data.local.entity.BaseEntity

@Entity(tableName = "genres")
data class GenresEntity(
    @PrimaryKey
    override val malId: Int,
    val name: String,
    val url: String,
    val count: Int
) : BaseEntity(malId)