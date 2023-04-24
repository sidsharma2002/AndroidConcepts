package com.example.androidconcepts.jcr.collections

import com.example.androidconcepts.common.measureTime
import org.junit.Test
import java.util.LinkedList
import java.util.TreeSet

/**
 * important link : https://www.geeksforgeeks.org/hashset-vs-treeset-in-java/
 */
class CollectionPerformanceTest {

    private val tenPower6 = 1000000 // gives out of java heap for greater than this

    @Test
    fun arrayListTest() {
        val arrayList = mutableListOf<Int>()
        measureTime {
            repeat(tenPower6) {
                arrayList.add(it)
            } // 20ms
        }

        measureTime {
            repeat(1000) {
                arrayList.add(arrayList.size / 2, it)
            } // 80ms
        }

        measureTime {
            repeat(1000) {
                arrayList.add(0, it)
            } // 300ms
        }
    }

    @Test
    fun linkedListTest() {
        val linkedList = LinkedList<Int>()

        measureTime {
            repeat(tenPower6) { // 10^6
                linkedList.add(it)
            } // 120ms
        }

        measureTime {
            repeat(1000) {
                linkedList.add(linkedList.size / 2, it)
            } // 1sec
        }

        measureTime {
            repeat(1000) {
                linkedList.add(0, it)
            } // 10ms
        }
    }

    @Test
    fun hashsetTest() {
        val hashset = HashSet<Int>() // unique

        measureTime {
            repeat(tenPower6) { // 10^6
                hashset.add(it)
            } // 85ms
        }

        measureTime {
            repeat(tenPower6) {
                hashset.contains(it)
            } // 50ms
        }
    }

    @Test
    fun linkedHashSetTest() {
        val linkedHashSet = java.util.LinkedHashSet<Int>() // unique

        measureTime {
            repeat(tenPower6) {
                linkedHashSet.add(it)
            } // 100ms
        }

        measureTime {
            repeat(tenPower6) {
                linkedHashSet.contains(it)
            } // 180ms
        }
    }

    @Test
    fun treeSetTest() {
        val treeSet = TreeSet<Int>() // sorted and unique

        measureTime {
            repeat(tenPower6) {
                treeSet.add(it)
            } // 150ms
        }
    }

    @Test
    fun hashmapTest() {
        val hashmap = HashMap<Int, String>()

        measureTime {
            repeat(tenPower6) {
                hashmap[it] = it.toString()
            } // 150ms
        }

        measureTime {
            repeat(tenPower6) {
                hashmap[it]
            } // 30ms
        }
    }
}