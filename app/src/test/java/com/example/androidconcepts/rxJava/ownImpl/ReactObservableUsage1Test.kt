package com.example.androidconcepts.rxJava.ownImpl

import com.example.androidconcepts.common.UiThreadPosterTD
import com.example.androidconcepts.rxJava.ownImpl.ReactObservable.*
import org.junit.Before
import org.junit.Test
import java.util.concurrent.Callable
import java.util.concurrent.CountDownLatch

class ReactObservableUsage1Test {

    private lateinit var SUT: ReactObservableUsage1
    private lateinit var uiThreadPosterTD: UiThreadPosterTD

    @Test
    fun subscribeTest() {

        var result = ""
        val latch = CountDownLatch(1)

        uiThreadPosterTD.post { // mocked android's ui thread

            SUT.fetchOBS()
                .setUiThreadPoster(uiThreadPosterTD)
                .subscribe(object : Observer<String> {
                    override fun onNext(data: String) {
                        result = data
                        latch.countDown()
                    }

                    override fun onError(throwable: Throwable) {
                        latch.countDown()
                    }
                })

        }

        latch.await()
        assert(result == "1")
    }

    @Test
    fun flatMapTest() {

        var result: Int = -1
        val latch = CountDownLatch(1)

        uiThreadPosterTD.post { // mocked android's ui thread

            SUT.fetchOBS()
                .setUiThreadPoster(uiThreadPosterTD)
                .flatMap {
                    SUT.mapToIntOBS(it)
                }
                .subscribe(object : Observer<Int> {
                    override fun onNext(data: Int) {
                        result = data
                        latch.countDown()
                    }

                    override fun onError(throwable: Throwable) {
                        latch.countDown()
                    }
                })

        }

        latch.await()
        assert(result == 1)
    }

    @Test
    fun multipleFlatMapsTest() {

        var result = ""
        val latch = CountDownLatch(1)

        uiThreadPosterTD.post {

            SUT.fetchOBS()
                .setUiThreadPoster(uiThreadPosterTD)
                .flatMap {
                    ReactObservable.fromCallable({
                        Thread.sleep(100L)
                        return@fromCallable 2.0
                    })
                }
                .flatMap {  // fixme : not working
                    ReactObservable.fromCallable({
                        Thread.sleep(100L)
                        return@fromCallable it.toString()
                    })
                }.subscribe(object : Observer<String> {
                    override fun onNext(data: String) {
                        result = data
                        latch.countDown()
                    }

                    override fun onError(throwable: Throwable) {
                        throw throwable
                    }
                })
        }

        latch.await()

        assert(result == "2.0")
    }

    @Before
    fun setup() {
        SUT = ReactObservableUsage1()
        uiThreadPosterTD = UiThreadPosterTD()
    }
}