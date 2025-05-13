package com.lambao.mrbeast.di

import com.lambao.mrbeast.data.repository.genres.GenresRepository
import com.lambao.mrbeast.data.repository.genres.GenresRepositoryImpl
import com.lambao.mrbeast.data.repository.seasons.SeasonsRepository
import com.lambao.mrbeast.data.repository.seasons.SeasonsRepositoryImpl
import com.lambao.mrbeast.data.repository.top.TopRepository
import com.lambao.mrbeast.data.repository.top.TopRepositoryImpl
import com.lambao.mrbeast.data.repository.watch.WatchRepository
import com.lambao.mrbeast.data.repository.watch.WatchRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideTopRepository(impl: TopRepositoryImpl): TopRepository = impl

    @Provides
    @Singleton
    fun provideSeasonsRepository(impl: SeasonsRepositoryImpl): SeasonsRepository = impl

    @Provides
    @Singleton
    fun provideGenresRepository(impl: GenresRepositoryImpl): GenresRepository = impl

    @Provides
    @Singleton
    fun provideWatchRepository(impl: WatchRepositoryImpl): WatchRepository = impl
}