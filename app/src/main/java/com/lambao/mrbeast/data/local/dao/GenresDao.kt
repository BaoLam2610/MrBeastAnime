package com.lambao.mrbeast.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.lambao.mrbeast.data.local.entity.genres.GenresEntity

@Dao
interface GenresDao : BaseDao<GenresEntity> {
    @Query("SELECT * FROM genres")
    fun getGenres(): List<GenresEntity>
}