package com.example.waterordersapp.di

import com.example.waterordersapp.data.repository.WaterRepositoryImpl
import com.example.waterordersapp.domain.repository.WaterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindWaterRepository(
        repository: WaterRepositoryImpl
    ): WaterRepository
}