package com.example.newsapp.data.repository

import android.content.SharedPreferences
import com.example.newsapp.domain.repository.PreferencesRepository
import javax.inject.Inject
import androidx.core.content.edit

// Data Layer: Implementation of the PreferencesRepository
class PreferencesRepositoryImpl @Inject constructor(private val sharedPreferences: SharedPreferences) :
    PreferencesRepository {

    override fun saveString(key: String, value: String) {
        sharedPreferences.edit { putString(key, value) }
    }

    override fun getString(key: String, defaultValue: String?): String? {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    override fun saveBoolean(key: String, value: Boolean) {
        sharedPreferences.edit { putBoolean(key, value) }
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }
}
