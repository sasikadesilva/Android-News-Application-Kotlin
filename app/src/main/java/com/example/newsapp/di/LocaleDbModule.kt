package com.example.newsapp.di

import android.content.Context
import androidx.room.Room
import com.example.newsapp.data.dao.UserDao
import com.example.newsapp.data.repository.UserRepositoryImpl
import com.example.newsapp.data.source.local.AppDatabase
import com.example.newsapp.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocaleDbModule {

    @Provides
    @Singleton
    fun provideDatabaseConnection(@ApplicationContext context: Context ): AppDatabase {

        val instance = Room.databaseBuilder<AppDatabase>( // Specify the type here
            context,
            AppDatabase::class.java,
            "news_app_db"
        ).build()
        return  instance
    }

    @Provides
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }
}