package com.example.newsapp.util

import java.security.SecureRandom
import java.security.spec.KeySpec
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import android.util.Base64

object PasswordHasher {

    private const val ITERATIONS = 65536
    private const val KEY_LENGTH = 256

    fun hashPassword(password: String, salt: ByteArray): String {
        val spec: KeySpec = PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH)
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val hash = factory.generateSecret(spec).encoded
        return Base64.encodeToString(hash, Base64.NO_WRAP)
    }

    fun generateSalt(): ByteArray {
        val salt = ByteArray(16)
        SecureRandom().nextBytes(salt)
        return salt
    }

    fun verifyPassword(input: String, storedHash: String, storedSalt: String): Boolean {
        val saltBytes = Base64.decode(storedSalt, Base64.NO_WRAP)
        val inputHash = hashPassword(input, saltBytes)
        return constantTimeEquals(inputHash, storedHash)
    }

    private fun constantTimeEquals(a: String, b: String): Boolean {
        if (a.length != b.length) return false
        var result = 0
        for (i in a.indices) {
            result = result or (a[i].code xor b[i].code)
        }
        return result == 0
    }

    fun encodeSalt(salt: ByteArray): String {
        return Base64.encodeToString(salt, Base64.NO_WRAP)
    }
}