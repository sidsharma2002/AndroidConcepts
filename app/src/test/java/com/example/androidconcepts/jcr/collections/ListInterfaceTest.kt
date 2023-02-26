package com.example.androidconcepts.jcr.collections

import com.example.androidconcepts.jcr.collections.listInterface.ListInterfaceExample1
import org.junit.Test

class ListInterfaceTest {

    private val SUT = ListInterfaceExample1()

    @Test
    fun mutableListTest() {
        SUT.stringList_ArrayList.add("3")
    }

    @Test
    fun immutableListTest() {
        try {
            SUT.stringList_UnmodifiableList.add("4")
        } catch (e: java.lang.Exception) {
            assert(e is UnsupportedOperationException)
        }
    }
}