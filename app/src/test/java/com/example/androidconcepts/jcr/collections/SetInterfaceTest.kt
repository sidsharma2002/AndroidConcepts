package com.example.androidconcepts.jcr.collections

import com.example.androidconcepts.jcr.collections.setInterface.SetInterfaceExample1
import org.junit.Test

class SetInterfaceTest {

    private val SUT = SetInterfaceExample1()

    @Test
    fun stringSetAlphaNumericInterfaceTest() {
        SUT.stringSetAlphaNumeric.forEach {
            println("set $it")
        }

        // prints : 1b,2c,4e,3a,5d
    }

    @Test
    fun stringSetNumericInterfaceTest() {
        SUT.stringSetNumeric.forEach {
            println("set $it")
        }

        // prints : 1,2,3,4,5
    }
}