package com.example.newsapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.newsapp.data.model.UserModel

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: UserModel)

    @Query("SELECT * FROM user")
    suspend fun getAllUsers(): List<UserModel>

    @Query("SELECT * FROM user WHERE email = :email")
    fun getUserByEmail(email: String): LiveData<UserModel>
}