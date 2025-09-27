package com.example.newsapp.data.repository

import com.example.newsapp.data.dao.UserDao
import com.example.newsapp.data.mapper.toDomain
import com.example.newsapp.data.mapper.toEntity
import com.example.newsapp.data.model.UserModel
import com.example.newsapp.data.source.local.AppDatabase
import com.example.newsapp.domain.repository.UserRepository
import javax.inject.Inject


class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao) : UserRepository {


    override suspend fun addUser(user: UserModel) {
        userDao.insertUser(user)
    }

    override suspend fun getUsers(): List<UserModel> {
        TODO("Not yet implemented")
    }
}