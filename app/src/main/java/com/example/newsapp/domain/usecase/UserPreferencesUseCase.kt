package com.example.newsapp.domain.usecase

import com.example.newsapp.domain.repository.PreferencesRepository
import javax.inject.Inject

class SaveUserPreferencesUseCase @Inject constructor(private val preferencesRepository: PreferencesRepository) {

    fun addPreferences(key: String, value: String) {
        preferencesRepository.saveString(key, value)

    }
}