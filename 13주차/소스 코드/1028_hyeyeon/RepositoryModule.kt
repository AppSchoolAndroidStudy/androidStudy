package com.androidstudy.hilt

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMemoRepository(
        database: FirebaseFirestore
    ): MemoRepository{
        return MemoRepositoryImp(database)
    }

}