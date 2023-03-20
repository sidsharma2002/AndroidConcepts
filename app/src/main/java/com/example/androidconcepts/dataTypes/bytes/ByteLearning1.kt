package com.example.androidconcepts.dataTypes.bytes

class ByteLearning1 {

    fun execute(): ByteArray {
        val data: Byte = 127 // 128 gives error

        val alphabets = "abcdefghijklmnopqrstuvwxyz"

//      for manually creating ByteArray...

//        val bArr = ByteArray(26)
//        alphabets.forEachIndexed { i, ch ->
//            bArr[i] = ch.code.toByte()
//        }

        return alphabets.toByteArray()
    }
}