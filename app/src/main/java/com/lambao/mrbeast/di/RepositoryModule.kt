package com.lambao.mrbeast.di

import com.lambao.mrbeast.data.repository.seasons.SeasonsRepository
import com.lambao.mrbeast.data.repository.seasons.SeasonsRepositoryImpl
import com.lambao.mrbeast.data.repository.top.TopRepository
import com.lambao.mrbeast.data.repository.top.TopRepositoryImpl
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
}