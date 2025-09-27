package com.example.newsapp.util

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import java.nio.charset.StandardCharsets

object CryptoUtil {

    private const val ALGORITHM = "AES/CBC/PKCS5Padding"
    private const val CHARSET = "UTF-8"
    private const val STATIC_KEY = "1234567890123456"

    // SecretKey from a static key string
    private fun getStaticKey(): SecretKey {
        return javax.crypto.spec.SecretKeySpec(STATIC_KEY.toByteArray(StandardCharsets.UTF_8), "AES")
    }

    // encrypt data using AES
    fun encrypt(data: String): String {
        val cipher = Cipher.getInstance(ALGORITHM)
        val iv = ByteArray(16)
        val ivSpec = IvParameterSpec(iv)

        cipher.init(Cipher.ENCRYPT_MODE, getStaticKey(), ivSpec)

        val encryptedBytes = cipher.doFinal(data.toByteArray(StandardCharsets.UTF_8))

        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
    }

    // decrypt data using AES
    fun decrypt(encryptedData: String): String {
        val cipher = Cipher.getInstance(ALGORITHM)
        val iv = ByteArray(16)
        val ivSpec = IvParameterSpec(iv)

        cipher.init(Cipher.DECRYPT_MODE, getStaticKey(), ivSpec)

        val decodedBytes = Base64.decode(encryptedData, Base64.DEFAULT)
        val decryptedBytes = cipher.doFinal(decodedBytes)

        return String(decryptedBytes, StandardCharsets.UTF_8)
    }
}
