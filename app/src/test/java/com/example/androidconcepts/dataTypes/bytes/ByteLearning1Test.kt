package com.example.androidconcepts.dataTypes.bytes

import org.junit.Before
import org.junit.Test

class ByteLearning1Test {

    private lateinit var SUT: ByteLearning1

    @Test
    fun simpleTest() {

        val result = SUT.execute()

        result.forEachIndexed { i, ch ->
            println("BYTE DEBUG : " + ('a' + i) + " " + ch)
        }

        /**
         *
        BYTE DEBUG : a 97
        BYTE DEBUG : b 98
        BYTE DEBUG : c 99
        BYTE DEBUG : d 100
        BYTE DEBUG : e 101
        BYTE DEBUG : f 102
        BYTE DEBUG : g 103
        BYTE DEBUG : h 104
        BYTE DEBUG : i 105
        BYTE DEBUG : j 106
        BYTE DEBUG : k 107
        BYTE DEBUG : l 108
        BYTE DEBUG : m 109
        BYTE DEBUG : n 110
        BYTE DEBUG : o 111
        BYTE DEBUG : p 112
        BYTE DEBUG : q 113
        BYTE DEBUG : r 114
        BYTE DEBUG : s 115
        BYTE DEBUG : t 116
        BYTE DEBUG : u 117
        BYTE DEBUG : v 118
        BYTE DEBUG : w 119
        BYTE DEBUG : x 120
        BYTE DEBUG : y 121
        BYTE DEBUG : z 122
         */
    }

    @Before
    fun setup() {
        SUT = ByteLearning1()
    }

}