package com.example.androidconcepts.coroutines.learning3

import android.R.attr.src
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Looper
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.logging.Handler
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis


class IOCallsOnDifferentDispatcher1 {

    private val urldisplay = "https://picsum.photos/200/300"

    private val singleThreadedDispatcher =
        Executors.newSingleThreadExecutor().asCoroutineDispatcher()
    private val fiveThreadedDispatcher = Executors.newFixedThreadPool(5).asCoroutineDispatcher()
    private val tenThreadedDispatcher = Executors.newFixedThreadPool(10).asCoroutineDispatcher()
    private val sixtyFourThreadedDispatcher = Executors.newFixedThreadPool(64).asCoroutineDispatcher()
    private val ioDispatcher = Dispatchers.IO
    private val defaultDispatcher = Dispatchers.Default

    fun startDownloading(context: Context, iterations: Int, noOfThreads: Int) =
        CoroutineScope(Dispatchers.Main).launch {

            val dispatcher = when (noOfThreads) {
                1 -> {
                    singleThreadedDispatcher
                }

                5 -> {
                    fiveThreadedDispatcher
                }

                10 -> {
                    tenThreadedDispatcher
                }

                64 -> {
                    sixtyFourThreadedDispatcher
                }

                -1 -> {
                    ioDispatcher
                }

                -2 -> {
                    defaultDispatcher
                }

                else -> {
                    Executors.newFixedThreadPool(noOfThreads).asCoroutineDispatcher()
                }
            }

            val time = measureTimeMillis {
                val list = mutableListOf<Job>()

                repeat(iterations) {
                    list += launch(dispatcher) {
                        try {
                            downloadBitmap(urldisplay)
                        } catch (e: Exception) {
                            e.printStackTrace()
                            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                            return@launch
                        }
                    }
                }

                list.joinAll()
            }

            Toast.makeText(context, "time : " + time, Toast.LENGTH_SHORT).show()
        }

    private val singleThreadPool =
        Executors.newSingleThreadExecutor()
    private val fiveThreadPool = Executors.newFixedThreadPool(5)
    private val tenThreadPool = Executors.newFixedThreadPool(10)
    private val ioThreadPool = Executors.newFixedThreadPool(64)

    fun startDownloadViaThreadPool(context: Context, noOfIterations: Int, noOfThreads: Int) {
        thread(start = true) {
            val threadPool = when (noOfThreads) {
                1 -> {
                    singleThreadPool
                }

                5 -> {
                    fiveThreadPool
                }

                10 -> {
                    tenThreadPool
                }

                64 -> {
                    ioThreadPool
                }

                else -> {
                    Executors.newFixedThreadPool(noOfThreads)
                }
            }

            val time = measureTimeMillis {
                val list = mutableListOf<Future<*>>()

                repeat(noOfIterations) {

                    list += threadPool.submit {
                        downloadBitmap(urldisplay)
                    }
                }

                list.forEach {
                    it.get()
                }
            }

            android.os.Handler(Looper.getMainLooper()).post {
                Toast.makeText(context, "time : " + time, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun downloadBitmap(urldisplay: String) {
        val url = URL(urldisplay)
        val connection = url.openConnection() as HttpURLConnection
        connection.connect()
        val input = connection.inputStream
        val bitmap = BitmapFactory.decodeStream(input)
        Log.d("IO_DEBUG", "" + bitmap.byteCount)
    }

}