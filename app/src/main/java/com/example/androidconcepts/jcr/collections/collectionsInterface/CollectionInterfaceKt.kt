package com.example.androidconcepts.jcr.collections.collectionsInterface

class CollectionInterfaceKt {
    private val stringCollection: Collection<String> = listOf("1", "2", "3")
    private val stringMutableCollection: MutableCollection<String> = mutableListOf()

    fun execute() {
        stringCollection.forEach {

        }

        // no get() method is provided
    }
}