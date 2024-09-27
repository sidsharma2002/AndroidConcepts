package com.example.androidconcepts.coroutines.performance

import com.example.androidconcepts.common.BgThreadPoster
import com.example.androidconcepts.common.measureTime
import kotlinx.coroutines.*
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.ThreadPoolExecutor
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

    @Test
    fun performanceTestOfNThreads() {
        val processImageUseCase = ProcessImageUseCase()
        val executor = Executors.newFixedThreadPool(8)

        val futures = mutableListOf<Future<*>>()

        measureTime {
            repeat(1000) {
                val result = executor.submit {
                    processImageUseCase.processBlocking(10)
                }

                futures += result
            }

            futures.forEach {
                it.get()
            }
        }

        // RESULTS :
        // 1005 Threads, 1000 repeat, 10ms task time -> 1291 ms
        // 500 Threads, 1000 repeat, 10ms task time -> 1036 ms
        // 100 Threads, 1000 repeat, 10ms task time -> 690 ms
        // 50 Threads, 1000 repeat, 10ms task time -> 649 ms
        // 8 (no of cores) Threads, 1000 repeat, 10ms task time -> 1262 seconds
        // 5 Threads, 1000 repeat, 10ms task time -> 2 seconds
    }

    @Test
    fun performanceTestOfNThreadedDispatcher() {
        val processImageUseCase = ProcessImageUseCase()
        val executor = Executors.newFixedThreadPool(5).asCoroutineDispatcher()
        val scope = CoroutineScope(executor)

        val jobs = mutableListOf<Job>()

        measureTime {
            runBlocking {
                repeat(1000) {
                    val result = scope.launch {
                        processImageUseCase.processBlocking(10)
                    }

                    jobs += result
                }

                jobs.joinAll()
            }
        }

        // RESULTS :
        // 1005 Threads, 1000 repeat, 10ms task time -> 1344 ms
        // 500 Threads, 1000 repeat, 10ms task time -> 1056 ms
        // 100 Threads, 1000 repeat, 10ms task time -> 785 ms
        // 50 Threads, 1000 repeat, 10ms task time -> 743 ms
        // 8 (no of cores) Threads, 1000 repeat, 10ms task time -> 1326 seconds
        // 5 Threads, 1000 repeat, 10ms task time -> 2 seconds
    }
}