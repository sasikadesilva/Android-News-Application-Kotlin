package com.example.newsapp.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.newsapp.NewsAppApplication
import com.example.newsapp.data.dao.ArticleDao
import com.example.newsapp.data.dao.UserDao
import com.example.newsapp.data.repository.PreferencesRepositoryImpl
import com.example.newsapp.data.source.local.AppDatabase
import com.example.newsapp.domain.repository.PreferencesRepository
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

    @Provides
    fun provideArticleDao(appDatabase: AppDatabase): ArticleDao {
        return appDatabase.articleDao()
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providePreferencesRepository(sharedPreferences: SharedPreferences): PreferencesRepository {
        return PreferencesRepositoryImpl(sharedPreferences)
    }
}