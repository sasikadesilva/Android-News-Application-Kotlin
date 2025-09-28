package com.example.newsapp.di

import android.content.Context
import androidx.room.Room
import com.example.newsapp.data.repository.ArticleRepositoryImpl
import com.example.newsapp.data.source.local.AppDatabase
import com.example.newsapp.data.source.remote.RetrofitClient
import com.example.newsapp.domain.repository.ArticleRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Provides
    @Singleton
    fun provideApiConnection(): RetrofitClient  {
        return  RetrofitClient()
    }


}