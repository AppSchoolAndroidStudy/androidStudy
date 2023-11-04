package com.test.retrofitex.repository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NasaModule {
    @Provides
    fun provideNasaRepository(nasaRepositoryImpl: NasaRepositoryImpl): NasaRepository{
        return nasaRepositoryImpl
    }
}