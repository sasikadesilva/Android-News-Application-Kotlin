package com.example.newsapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.model.UserModel
import com.example.newsapp.domain.usecase.UserPreferencesUseCase
import com.example.newsapp.domain.usecase.UserRegisterUseCase
import com.example.newsapp.domain.usecase.UserVerificationUseCase
import com.example.newsapp.util.USER_LOGIN_SESSION
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val addUserUseCase: UserRegisterUseCase,
    private val userVerificationUseCase: UserVerificationUseCase,
    private val userPreferencesUseCase: UserPreferencesUseCase
) : ViewModel() {

    private val _registrationSuccess = MutableLiveData<Boolean>()
    val registrationSuccess: LiveData<Boolean> = _registrationSuccess

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun registerUser(user: UserModel) {
        viewModelScope.launch {
            try {
                addUserUseCase(user)
                _registrationSuccess.postValue(true)
            } catch (exception: Exception) {
                // Post the error message to the LiveData instead of crashing the app
                _error.postValue(exception.message)
            }
        }
    }

    fun getUserByEmail(email: String): LiveData<UserModel>? {
        return userVerificationUseCase(email)
    }

    suspend fun getUserSessionInfo(): UserModel? {
        val user = userPreferencesUseCase.getStringPreferences(USER_LOGIN_SESSION, true)
        val userModel = Gson().fromJson(user, UserModel::class.java)
        return userModel
    }

    fun addUserSessionInfo(user: UserModel?) {
        viewModelScope.launch {
            val userString = Gson().toJson(user)
            userPreferencesUseCase.addPreferences(USER_LOGIN_SESSION, userString, true)
        }

    }
}