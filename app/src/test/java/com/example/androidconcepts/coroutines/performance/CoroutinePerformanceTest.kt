package com.example.androidconcepts.coroutines.performance

import kotlinx.coroutines.*
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class CoroutinePerformanceTest {

    @Test
    fun performanceIssueWithDefaultDispatcher() = runBlocking {

        val processImageUseCase = ProcessImageUseCase()
        val addImageFiltersUseCase = AddImageFiltersUseCase()
        val coroutineScope = CoroutineScope(Dispatchers.Default)
        val noOfIterations = Runtime.getRuntime().availableProcessors() * 100 // to saturate default dispatcher
        val eachTaskTime = 100
        val allTasksPostedCDL = CountDownLatch(noOfIterations)
        val startTime = System.currentTimeMillis()

        println(noOfIterations)

        val backgroundJob = coroutineScope.launch {
            // simulate background processing of images
            repeat(noOfIterations) {
                launch {
                    processImageUseCase.process(eachTaskTime)
                }

                allTasksPostedCDL.countDown()
            }
        }

        allTasksPostedCDL.await(eachTaskTime * noOfIterations * 1L, TimeUnit.SECONDS)

        // simulate user action
        val userJob = coroutineScope.launch {
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