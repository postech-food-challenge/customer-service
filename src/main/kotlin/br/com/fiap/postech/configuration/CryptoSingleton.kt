package br.com.fiap.postech.configuration

import io.ktor.server.config.*
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object CryptoSingleton {
    private lateinit var KEY: SecretKey;
    private const val ALGORITHM = "AES/CBC/PKCS5Padding"
    private val iv = ByteArray(16)

    fun init(config: ApplicationConfig) {
        KEY = getSecretKeyFromEnv(config.property("cipher.key").getString())
    }

    private fun getSecretKeyFromEnv(keyString : String): SecretKey {
        val decodedKey = Base64.getDecoder().decode(keyString)
        return SecretKeySpec(decodedKey, 0, decodedKey.size, "AES")
    }

    fun encrypt(input: String): String {
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, KEY, IvParameterSpec(iv))
        val encryptedBytes = cipher.doFinal(input.toByteArray())
        return Base64.getEncoder().encodeToString(encryptedBytes)
    }

    fun decrypt(input: String): String {
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, KEY, IvParameterSpec(iv))
        val decodedBytes = Base64.getDecoder().decode(input)
        val decryptedBytes = cipher.doFinal(decodedBytes)
        return String(decryptedBytes)
    }
}