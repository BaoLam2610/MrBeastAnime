package com.lambao.mrbeast.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lambao.mrbeast.data.local.dao.GenresDao
import com.lambao.mrbeast.data.local.entity.genres.GenresEntity

@Database(
    entities = [
        GenresEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun genresDao(): GenresDao
}
