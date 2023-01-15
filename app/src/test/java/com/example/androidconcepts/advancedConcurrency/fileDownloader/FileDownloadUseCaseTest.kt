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

    @Test
    fun onSubmittingSameFileTwiceSync_downloadsOnlyOnce() {
        // arrange
        val semaphore1 = Semaphore(0)
        val semaphore2 = Semaphore(0)

        val file = File("file1", "some url1")

        // act
        SUT.startDownloadFileAsync(file, object : FileDownloadUseCase.Listener {
            override fun onFileResult(downloadedFile: DownloadedFile) {
                semaphore1.release()
            }
        })

        semaphore1.acquire()

        SUT.startDownloadFileAsync(file, object : FileDownloadUseCase.Listener {
            override fun onFileResult(downloadedFile: DownloadedFile) {
                semaphore2.release()
            }
        })

        semaphore2.acquire()

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
        SUT.startDownloadFileAsync(file, object : FileDownloadUseCase.Listener {
            override fun onFileResult(downloadedFile: DownloadedFile) {
                semaphore.release()
            }
        })

        SUT.startDownloadFileAsync(file, object : FileDownloadUseCase.Listener {
            override fun onFileResult(downloadedFile: DownloadedFile) {
                semaphore.release()
            }
        })

        SUT.startDownloadFileAsync(file, object : FileDownloadUseCase.Listener {
            override fun onFileResult(downloadedFile: DownloadedFile) {
                semaphore.release()
            }
        })

        semaphore.acquire()

        // assert
        verify(exactly = 1) {
            fileDownloader.downloadFileSync(file)
        }
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