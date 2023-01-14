package com.example.androidconcepts.advancedConcurrency.fileDownloaded

import com.example.androidconcepts.common.BgThreadPoster
import com.example.androidconcepts.common.UiThreadPoster
import java.util.Collections
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

class FileDownloadUseCase constructor(
    private val bgThreadPoster: BgThreadPoster = BgThreadPoster(),
    private val uiThreadPoster: UiThreadPoster = UiThreadPoster(),
    private val fileDownloader: FileDownloader = FileDownloaderImpl(bgThreadPoster, uiThreadPoster)
) {
    interface Listener {
        fun onFileResult(downloadedFile: DownloadedFile)
    }

    private val currentlyDownloadingFileNames: MutableList<String> =
        Collections.synchronizedList(listOf())

    private val cachedFiles: ConcurrentHashMap<String, DownloadedFile> = ConcurrentHashMap()

    private val lock: Lock = ReentrantLock()

    fun startDownloadFileAsync(file: File, listener: Listener) = bgThreadPoster.post {

        if (cachedFiles.contains(file.name)) {
            uiThreadPoster.post {
                listener.onFileResult(cachedFiles[file.name]!!)
            }
        }

        val downloadedFile = performDownloadSync(file)
        cachedFiles[file.name] = downloadedFile

        uiThreadPoster.post {
            listener.onFileResult(downloadedFile)
        }
    }

    private fun performDownloadSync(file: File): DownloadedFile {
        val countDownLatch = CountDownLatch(1)
        lateinit var downloadedFile1: DownloadedFile

        fileDownloader.downloadFileAsync(file, object : FileDownloader.Listener {
            override fun onFileDownloaded(downloadedFile: DownloadedFile) {
                downloadedFile1 = downloadedFile
                countDownLatch.countDown()
            }
        })

        countDownLatch.await(2, TimeUnit.SECONDS)
        return downloadedFile1
    }
}