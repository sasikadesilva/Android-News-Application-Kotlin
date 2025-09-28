package com.example.newsapp.domain.usecase

import androidx.lifecycle.LiveData
import com.example.newsapp.data.model.entity.UserModel
import com.example.newsapp.domain.repository.UserRepository
import javax.inject.Inject

class UserVerificationUseCase @Inject constructor(private val repo: UserRepository) {

    operator fun invoke(email: String) : LiveData<UserModel>?  {
        return repo.getUserByEmail(email)
    }
}