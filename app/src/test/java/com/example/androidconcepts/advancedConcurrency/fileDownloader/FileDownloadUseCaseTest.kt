package com.example.androidconcepts.advancedConcurrency.fileDownloader

import com.example.androidconcepts.common.BgThreadPoster
import com.example.androidconcepts.common.UiThreadPosterTD
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Semaphore
import java.util.concurrent.TimeUnit

class FileDownloadUseCaseTest {

    private var downloadedFile: DownloadedFile? = null
    private val noopListener = object : FileDownloadUseCase.Listener {
        override fun onFileResult(downloadedFile: DownloadedFile) {
            /* no-op */
        }
    }

    @Test
    fun onSubmittingSameFileTwiceSync_downloadsOnlyOnce() {
        // arrange
        val file = File("file1", "some url1")

        // act
        SUT.startDownloadFileAsync(file, noopListener).get()
        SUT.startDownloadFileAsync(file, noopListener).get()

        // assert
        verify(exactly = 1) {
            fileDownloader.downloadFileSync(file)
        }
    }

    @Test
    fun onSubmittingSameFileThrice_downloadsOnlyOnce() {
        // arrange
        val file = File("file1", "some url1")
        val semaphore = Semaphore(-2)

        // act
        repeat(3) {
            Thread {
                SUT.startDownloadFileAsync(file, object : FileDownloadUseCase.Listener {
                    override fun onFileResult(downloadedFile: DownloadedFile) {
                        semaphore.release()
                    }
                })
            }.start()
        }

        semaphore.acquire()

        // assert
        verify(exactly = 1) {
            fileDownloader.downloadFileSync(file)
        }
    }

    @Test
    fun performanceTestWhenDownloadingFiveDifferentFiles() {
        // arrange
        val semaphore = Semaphore(-4)

        // act
        repeat(5) {
            Thread {
                val file = File("file$it", "some url$it")

                SUT.startDownloadFileAsync(file, object : FileDownloadUseCase.Listener {
                    override fun onFileResult(downloadedFile: DownloadedFile) {
                        semaphore.release()
                    }
                })
            }.start()
        }

        semaphore.acquire() // should be less than 2.5 secs (500 * 5)
    }

    private lateinit var SUT: FileDownloadUseCase
    private lateinit var fileDownloader: FileDownloaderTD

    @Before
    fun setup() {
        downloadedFile = null
        val bgThreadPoster = BgThreadPoster()
        val uiThreadPoster = UiThreadPosterTD()
        fileDownloader = mockk(relaxed = true)
        SUT = FileDownloadUseCase(bgThreadPoster, uiThreadPoster, fileDownloader)
    }
}