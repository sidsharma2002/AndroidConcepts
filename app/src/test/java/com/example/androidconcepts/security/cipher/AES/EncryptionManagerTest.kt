package com.example.androidconcepts.security.cipher.AES

import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class EncryptionManagerTest {

    @Test
    fun encryptionAndDecryptionWorkingCorrectly() {
        val SUT = EncryptionManager()

        val key = SUT.generateKeyUsingKeyGenerator()
        val iv = SUT.generateIV()
        val message = "some_secret_message"

        val cipherText = SUT.encrypt(message, key, iv)

        val decryptedMessage = SUT.decrypt(cipherText, key, iv)

        assert(message.equals(decryptedMessage))
    }

}