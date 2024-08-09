package br.com.fiap.postech.configuration

import io.ktor.server.config.*
import java.security.SecureRandom
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

object CryptoSingleton {
    private lateinit var KEY: SecretKey;
    private const val ALGORITHM = "AES/GCM/NoPadding"
    private const val IV_LENGTH = 12

    fun init(config: ApplicationConfig) {
        KEY = getSecretKeyFromEnv(config.property("cipher.key").getString())
    }

    private fun getSecretKeyFromEnv(keyString : String): SecretKey {
        val decodedKey = Base64.getDecoder().decode(keyString)
        return SecretKeySpec(decodedKey, 0, decodedKey.size, "AES")
    }

    fun encrypt(input: String): String {
        val cipher = Cipher.getInstance(ALGORITHM)
        val iv = ByteArray(IV_LENGTH)
        SecureRandom().nextBytes(iv)

        val gcmSpec = GCMParameterSpec(128, iv)
        cipher.init(Cipher.ENCRYPT_MODE, KEY, gcmSpec)

        val encryptedBytes = cipher.doFinal(input.toByteArray())

        val ivAndCiphertext = iv + encryptedBytes
        return Base64.getEncoder().encodeToString(ivAndCiphertext)
    }

    fun decrypt(input: String): String {
        val decodedBytes = Base64.getDecoder().decode(input)

        val iv = decodedBytes.sliceArray(0 until IV_LENGTH)
        val ciphertext = decodedBytes.sliceArray(IV_LENGTH until decodedBytes.size)

        val cipher = Cipher.getInstance(ALGORITHM)
        val gcmSpec = GCMParameterSpec(128, iv)
        cipher.init(Cipher.DECRYPT_MODE, KEY, gcmSpec)

        val decryptedBytes = cipher.doFinal(ciphertext)
        return String(decryptedBytes)
    }
}