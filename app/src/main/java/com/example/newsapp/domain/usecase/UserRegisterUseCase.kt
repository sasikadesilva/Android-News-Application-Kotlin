package com.example.newsapp.domain.usecase

import com.example.newsapp.data.model.UserModel
import com.example.newsapp.data.repository.UserRepositoryImpl
import com.example.newsapp.util.PasswordHasher
import javax.inject.Inject


class UserRegisterUseCase @Inject constructor(private val repo: UserRepositoryImpl){

    suspend operator fun invoke(user: UserModel)  {
        if (user.firstName.isEmpty()) {
            throw Exception("First name cannot be empty")
        }
        if (user.lastName.isEmpty()) {
            throw Exception("Last name cannot be empty")
        }
        if (user.email.isEmpty()) {
            throw Exception("Email cannot be empty")
        }
        if (user.password.isEmpty()) {
            throw Exception("Password cannot be empty")
        }
        if (user.password.length < 8) {
            throw Exception("Password must be at least 8 characters long")
        }
        if (!user.email.contains("@")) {
            throw Exception("Invalid email address")
        }
        if (!user.email.contains(".")) {
            throw Exception("Invalid email address")
        }

       val salt = PasswordHasher.generateSalt()
        val hashedPassword = PasswordHasher.hashPassword(user.password, salt)
        user.password = hashedPassword
        user.passwordSalt = PasswordHasher.encodeSalt( salt)

        repo.addUser(user)
    }
}