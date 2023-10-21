package com.qure.calculator_tdd.data.di

import android.content.Context
import com.qure.calculator_tdd.data.HistoryDao
import com.qure.calculator_tdd.data.HistoryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {

    @Provides
    @Singleton
    fun providerHistoryDatabase(@ApplicationContext context: Context): HistoryDatabase {
        return HistoryDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun providerHistoryDao(database: HistoryDatabase): HistoryDao {
        return database.historyDao()
    }
}