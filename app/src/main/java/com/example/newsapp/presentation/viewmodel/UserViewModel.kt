package com.example.newsapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.model.UserModel
import com.example.newsapp.domain.usecase.UserRegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val addUserUseCase: UserRegisterUseCase
) : ViewModel() {

    fun registerUser(user : UserModel) {
        viewModelScope.launch {
            try {
                addUserUseCase(user)
            }catch (exception: Exception){
                throw Exception(exception.message)
            }

        }
    }
}