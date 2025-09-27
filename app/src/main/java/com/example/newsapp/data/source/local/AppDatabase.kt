package com.example.newsapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsapp.data.dao.UserDao
import com.example.newsapp.data.model.UserModel

@Database(
    entities = [
        UserModel::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}