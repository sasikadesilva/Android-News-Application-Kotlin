package com.example.newsapp.domain.usecase

import androidx.lifecycle.LiveData
import com.example.newsapp.data.model.UserModel
import com.example.newsapp.data.repository.UserRepositoryImpl
import javax.inject.Inject

class UserVerificationUseCase @Inject constructor(private val repo: UserRepositoryImpl) {

    operator fun invoke(email: String) : LiveData<UserModel>?  {
        return repo.getUserByEmail(email)
    }
}