package com.example.newsapp.domain.usecase

import com.example.newsapp.domain.repository.PreferencesRepository
import com.example.newsapp.util.CryptoUtil
import javax.inject.Inject

class UserPreferencesUseCase @Inject constructor(private val preferencesRepository: PreferencesRepository) {

    suspend fun addPreferences(key: String, value: String,isEncrypted: Boolean) {
        if(isEncrypted){
            preferencesRepository.saveString(key, CryptoUtil.encrypt(value))
        }
        else{
            preferencesRepository.saveString(key, value)
        }
    }

    suspend fun getStringPreferences(key: String, isEncrypted: Boolean) : String? {
        val storedValue = preferencesRepository.getString(key,null) ?: return null

        if(isEncrypted){
            return try {
                CryptoUtil.decrypt(storedValue)
            } catch (e: Exception) {
                // If decryption fails, the data is corrupt or was not encrypted.
                // Return null for safety.
                null
            }
        }
        return storedValue
    }
}
