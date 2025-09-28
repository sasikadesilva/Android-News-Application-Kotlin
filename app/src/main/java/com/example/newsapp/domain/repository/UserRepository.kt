package com.example.newsapp.domain.repository

import androidx.lifecycle.LiveData
import com.example.newsapp.data.model.entity.UserModel

interface UserRepository {
    suspend fun addUser(user: UserModel)
    suspend fun getUsers(): List<UserModel>

    fun getUserByEmail(email: String): LiveData<UserModel>?

}