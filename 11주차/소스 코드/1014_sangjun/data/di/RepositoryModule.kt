package com.qure.calculator_tdd.data.di

import com.qure.calculator_tdd.data.HistoryRepositoryImpl
import com.qure.calculator_tdd.domain.HistoryRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsMemoryRepository(
        historyRepositoryImpl: HistoryRepositoryImpl
    ): HistoryRepository
}