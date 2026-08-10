package com.example.waterordersapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideJson() : Json {
        return Json {
            prettyPrint = true
            ignoreUnknownKeys = true
        }
    }

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context) : Context {
        return context
    }
}