package com.example.newsapp.domain.repository

interface PreferencesRepository {

    fun saveString(key: String, value: String)
    fun getString(key: String, defaultValue: String?): String?
    fun saveBoolean(key: String, value: Boolean)
    fun getBoolean(key: String, defaultValue: Boolean): Boolean
}