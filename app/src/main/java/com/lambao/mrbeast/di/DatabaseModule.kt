package com.lambao.mrbeast.di

import android.content.Context
import androidx.room.Room
import com.lambao.mrbeast.data.local.AppDatabase
import com.lambao.mrbeast.utils.Constants.Database.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        DB_NAME
    ).fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideGenresDao(db: AppDatabase) = db.genresDao()
}