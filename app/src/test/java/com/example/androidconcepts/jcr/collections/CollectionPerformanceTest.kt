package com.example.androidconcepts.jcr.collections

import org.junit.Test
import java.util.LinkedList
import kotlin.math.exp

class CollectionPerformanceTest {

    private val TenPow6 = 1000000

    @Test
    fun arrayListTest() {
        val arrayList = mutableListOf<Int>()
        repeat(TenPow6) { // 10^6
            arrayList.add(it)
        } // 20ms

        repeat(1000) {
            arrayList.add(arrayList.size / 2, it)
        } // 80ms

        repeat(1000) {
            arrayList.add(0, it)
        } // 300ms
    }

    @Test
    fun linkedListTest() {
        val linkedList = LinkedList<Int>()
        repeat(TenPow6) { // 10^6
            linkedList.add(it)
        } // 120ms

        repeat(1000) {
            linkedList.add(linkedList.size / 2, it)
        } // 1sec

        repeat(1000) {
            linkedList.add(0, it)
        } // 10sec
    }

    @Test
    fun hashsetTest() {
        val hashset = HashSet<Int>() // unique and sorted

        repeat(TenPow6) { // 10^6
            hashset.add(it)
        } // 85ms

        hashset.clear()

        repeat(20) {
            hashset.add((0..4).random())
        }

        hashset.forEach {
            println(it)
        }
    }

    @Test
    fun linkedHashSet() {
        val linkedHashSet = java.util.LinkedHashSet<Int>() // unique but not sorted

        repeat(20) {
            linkedHashSet.add((0..4).random())
        }

        linkedHashSet.forEach {
            println(it)
        }
    }
}