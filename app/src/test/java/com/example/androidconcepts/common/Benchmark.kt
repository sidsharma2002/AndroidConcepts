package com.example.androidconcepts.common

fun measureTime(action: () -> Unit): Long {
    val startTime = System.currentTimeMillis()
    action.invoke()
    val endTime = System.currentTimeMillis()
    val measuredTime = endTime - startTime
    println("measured time = " + measuredTime + "ms")
    return measuredTime
}