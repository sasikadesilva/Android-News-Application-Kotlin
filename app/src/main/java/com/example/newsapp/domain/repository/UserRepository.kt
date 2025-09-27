package com.example.newsapp.domain.repository

import com.example.newsapp.data.model.UserModel

interface UserRepository {
    suspend fun addUser(user: UserModel)
    suspend fun getUsers(): List<UserModel>
}