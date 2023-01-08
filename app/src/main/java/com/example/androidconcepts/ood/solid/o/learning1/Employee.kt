package com.example.androidconcepts.ood.solid.o.learning1

data class Employee(
    val name: String,
    val type: Int
) {
    companion object {
        const val FREELANCE = 1
        const val FULLTIME = 2
        const val INTERN = 3
    }
}
