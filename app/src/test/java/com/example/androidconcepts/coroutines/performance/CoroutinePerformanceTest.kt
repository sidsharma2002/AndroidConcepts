package com.example.androidconcepts.coroutines.performance

import kotlinx.coroutines.*
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class CoroutinePerformanceTest {

    @Test
    fun performanceTestOfDefaultDispatcher() = runBlocking {

        val processImageUseCase = ProcessImageUseCase()
        val addImageFiltersUseCase = AddImageFiltersUseCase()
        val coroutineScope = CoroutineScope(Dispatchers.Default)
        val noOfIterations = Runtime.getRuntime().availableProcessors() // to saturate default dispatcher
        val eachTaskTime = 5000
        val allTasksPostedCDL = CountDownLatch(noOfIterations)
        val startTime = System.currentTimeMillis()

        println(noOfIterations)

        val backgroundJob = coroutineScope.launch {
            // simulate background processing of images
            repeat(noOfIterations) {
                launch {
                    println("task1 " + Thread.currentThread().id)
                    processImageUseCase.process(eachTaskTime)
                }

                allTasksPostedCDL.countDown()
            }
        }

        println("posted all tasks in ${System.currentTimeMillis() - startTime} ms")
        allTasksPostedCDL.await()

        // simulate user action
        val userJob = coroutineScope.launch {
            println("task2 " + Thread.currentThread().id)
            addImageFiltersUseCase.addFilters()
        }

        backgroundJob.invokeOnCompletion {
            println("background processing completed in: ${System.currentTimeMillis() - startTime} ms")
        }

        userJob.invokeOnCompletion {
            println("user action latency: ${System.currentTimeMillis() - startTime} ms")
        }

        joinAll(backgroundJob, userJob)
    }

    @Test
    fun performanceTestOfSingleThreadedDispatcher() = runBlocking {
        val processImageUseCase = ProcessImageUseCase()
        val addImageFiltersUseCase = AddImageFiltersUseCase()

        val boundedDispatcher = Executors.newFixedThreadPool(1).asCoroutineDispatcher()
        val coroutineScope = CoroutineScope(boundedDispatcher)
        val noOfIterations = Runtime.getRuntime().availableProcessors() // to saturate default dispatcher
        val eachTaskTime = 2000
        val allTasksPostedCDL = CountDownLatch(noOfIterations)
        val startTime = System.currentTimeMillis()

        println(noOfIterations)

        val backgroundJob = coroutineScope.launch {
            // simulate background processing of images
            repeat(noOfIterations) {
                launch {
                    println("task1 " + Thread.currentThread().id)
                    processImageUseCase.process(eachTaskTime)
                }

                allTasksPostedCDL.countDown()
            }
        }

        println("posted all tasks in ${System.currentTimeMillis() - startTime} ms")
        allTasksPostedCDL.await()

        // simulate user action
        val userJob = coroutineScope.launch {
            println("task2 " + Thread.currentThread().id)
            addImageFiltersUseCase.addFilters()
        }

        backgroundJob.invokeOnCompletion {
            println("background processing completed in: ${System.currentTimeMillis() - startTime} ms")
        }

        userJob.invokeOnCompletion {
            println("user action latency: ${System.currentTimeMillis() - startTime} ms")
        }

        joinAll(backgroundJob, userJob)
    }
}