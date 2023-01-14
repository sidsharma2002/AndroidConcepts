package com.example.androidconcepts.advancedConcurrency.fileDownloaded

import com.example.androidconcepts.common.BgThreadPoster
import com.example.androidconcepts.common.UiThreadPosterTD
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class FileDownloadUseCaseTest {

    private var downloadedFile: DownloadedFile? = null

    @Test
    fun onSubmittingSameFileFromMultipleThreads_startsDownloadOnlyOnce() {
        // arrange
        val file = File("file1", "some url1")
        val countDownLatch = CountDownLatch(2)

        // act
        Thread {
            SUT.startDownloadFileAsync(file, object : FileDownloadUseCase.Listener {
                override fun onFileResult(downloadedFile: DownloadedFile) {
                    this@FileDownloadUseCaseTest.downloadedFile = downloadedFile
                    countDownLatch.countDown()
                }
            })
        }.start()

        Thread {
            SUT.startDownloadFileAsync(file, object : FileDownloadUseCase.Listener {
                override fun onFileResult(downloadedFile: DownloadedFile) {
                    this@FileDownloadUseCaseTest.downloadedFile = downloadedFile
                    countDownLatch.countDown()
                }
            })
        }.start()

        countDownLatch.await(3, TimeUnit.SECONDS)

        // assert
        verify(exactly = 1) {
            fileDownloader.downloadFileAsync(file, any())
        }
    }

    private lateinit var SUT: FileDownloadUseCase
    private lateinit var fileDownloader: FileDownloader

    @Before
    fun setup() {
        downloadedFile = null
        fileDownloader = mockk()
        SUT = FileDownloadUseCase(BgThreadPoster(), UiThreadPosterTD(), fileDownloader)
    }
}