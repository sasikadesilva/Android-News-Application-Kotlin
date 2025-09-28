package com.example.newsapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsapp.data.dao.ArticleDao
import com.example.newsapp.data.dao.UserDao
import com.example.newsapp.data.model.entity.Article
import com.example.newsapp.data.model.entity.UserModel

@Database(
    entities = [
        UserModel::class,
        Article::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun articleDao(): ArticleDao
}