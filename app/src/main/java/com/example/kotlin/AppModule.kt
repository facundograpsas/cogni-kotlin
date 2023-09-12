package com.example.kotlin

import android.content.Context
import com.example.kotlin.data.TipsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideTipsRepository(@ApplicationContext context: Context): TipsRepository {
        return TipsRepository(context)
    }
}