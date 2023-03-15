package com.example.androidconcepts.livedata.learning2

import com.example.androidconcepts.common.UiThreadPosterTD
import com.example.androidconcepts.livedata.learning2.ObservableDataHolder
import org.junit.Before
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class ObservableDataHolderTest {

    private lateinit var SUT: ObservableDataHolder<Int>
    private lateinit var uiThreadPosterTD: UiThreadPosterTD

    @Test
    fun observerRegistered_valueIsSet_valueGetsObserved() {

        var observedData = -1
        val latch = CountDownLatch(1)

        uiThreadPosterTD.post {
            SUT.registerListener(object : ObservableDataHolder.Observer<Int> {
                override fun onValueChanged(data: Int) {
                    observedData = data
                    latch.countDown()
                }
            })

            SUT.setData(1)
        }

        assert(latch.await(1, TimeUnit.SECONDS))
        assert(observedData == 1)
    }

    @Test
    fun noValueIsSet_observerDoesNotObserveAnyValue() {

        val latch = CountDownLatch(1)

        uiThreadPosterTD.post {
            SUT.registerListener(object : ObservableDataHolder.Observer<Int> {
                override fun onValueChanged(data: Int) {
                    latch.countDown()
                }
            })
        }

        assert(!latch.await(100, TimeUnit.MILLISECONDS))
    }

    @Test
    fun observerRegistered_multipleValuesAreSet_valuesGetObservedCorrectly() {

        val observedValues: MutableList<Int> = mutableListOf()
        val latch = CountDownLatch(5)

        uiThreadPosterTD.post {
            SUT.registerListener(object : ObservableDataHolder.Observer<Int> {
                override fun onValueChanged(data: Int) {
                    observedValues.add(data)
                    latch.countDown()
                }
            })

            SUT.setData(1)
            SUT.setData(2)
            SUT.setData(3)
            SUT.setData(4)
            SUT.setData(5)
        }

        latch.await(1, TimeUnit.SECONDS)

        val expectedList: List<Int> = List(5) {
            it + 1 // start from 1
        }

        assert(observedValues == expectedList)
    }

    @Test
    fun longRunningObservers_doesNotBlockSetValue() {

        val latch = CountDownLatch(100)

        uiThreadPosterTD.post {

            registerLongRunningObservers(noOfObservers = 100)

            repeat(100) {
                Thread {
                    SUT.setData(it)
                    latch.countDown()
                }.start()
            }
        }

        assert(latch.await(50, TimeUnit.MILLISECONDS))
    }

    private fun registerLongRunningObservers(noOfObservers: Int) {
        repeat(noOfObservers) {
            SUT.registerListener(object : ObservableDataHolder.Observer<Int> {
                override fun onValueChanged(data: Int) {
                    Thread.sleep(100L) // sleep for straight n * 100 millis
                }
            })
        }
    }

    @Test
    fun dataSetAfterObserverIsUnregistered_notifiesOfMissedNotification() {

        var observedData: Int? = null
        val latch = CountDownLatch(2)

        uiThreadPosterTD.post {

            val observer = object : ObservableDataHolder.Observer<Int> {
                override fun onValueChanged(data: Int) {
                    observedData = data
                    latch.countDown()
                }
            }

            // onStart
            SUT.registerListener(observer)

            // onStop
            SUT.unregisterListener(observer)

            // data is fetched from api and set to dataHolder.
            SUT.setData(1)

            // onStart
            SUT.registerListener(observer)
        }

        assert(latch.await(1, TimeUnit.SECONDS))
        assert(observedData == 1)
    }

    @Before
    fun setup() {
        uiThreadPosterTD = UiThreadPosterTD()
        SUT = ObservableDataHolder(uiThreadPoster = uiThreadPosterTD)
    }
}