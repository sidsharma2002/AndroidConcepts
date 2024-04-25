package com.example.androidconcepts.security.cipher.AES

import android.os.Build
import androidx.annotation.RequiresApi
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

@RequiresApi(Build.VERSION_CODES.O)
class EncryptionManager {

    fun encrypt(inputString: String, key: SecretKey, iv: IvParameterSpec): String {

        // setup cipher instance
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding") // encryption algo / mode / padding algo
        cipher.init(Cipher.ENCRYPT_MODE, key, iv)

        // encrypt
        val cipherText = cipher.doFinal(inputString.toByteArray())

        // return encrypted string
        return Base64.getEncoder().encodeToString(cipherText)
    }

    fun decrypt(cipherText: String, key: SecretKey, iv: IvParameterSpec): String {

        // setup cipher instance
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, key, iv)

        // decrypt
        val decryptedText = cipher.doFinal(Base64.getDecoder().decode(cipherText))

        return String(decryptedText)
    }

    @Throws(NoSuchAlgorithmException::class)
    fun generateKeyUsingKeyGenerator(): SecretKey {
         val keyGenerator = KeyGenerator.getInstance("AES")
        keyGenerator.init(128)
        return keyGenerator.generateKey()
    }

    private fun generateKeyUsingPassphrase(passPhrase: String): SecretKey {
        // generate salt
        val salt = generateSalt()

        // define hashing function and key specs
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val specs = PBEKeySpec(passPhrase.toCharArray(), salt.toByteArray(), 65536, 128)

        // generate secret key
        val secret = SecretKeySpec(factory.generateSecret(specs).encoded, "AES")
        return secret
    }

    private fun generateSalt(): String {
        val bytesArray = ByteArray(16)
        SecureRandom().nextBytes(bytesArray)
        return Base64.getEncoder().encodeToString(bytesArray)
    }

    fun generateIV(): IvParameterSpec {
        val bytesArray = ByteArray(16)
        SecureRandom().nextBytes(bytesArray)
        return IvParameterSpec(bytesArray)
    }

}