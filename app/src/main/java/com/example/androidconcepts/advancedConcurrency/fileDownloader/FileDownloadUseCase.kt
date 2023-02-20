package com.example.androidconcepts.advancedConcurrency.fileDownloader

import androidx.annotation.WorkerThread
import com.example.androidconcepts.common.BgThreadPoster
import com.example.androidconcepts.common.UiThreadPoster
import java.util.concurrent.Future
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class FileDownloadUseCase constructor(
    private val bgThreadPoster: BgThreadPoster = BgThreadPoster(),
    private val uiThreadPoster: UiThreadPoster = UiThreadPoster(),
    private val fileDownloader: FileDownloader = FileDownloaderImpl()
) {
    interface Listener {
        fun onFileResult(downloadedFile: DownloadedFile)
    }

    private val cachedFiles: HashMap<String, DownloadedFile> =
        hashMapOf() // concurrent hashmap doesn't work here for some reason but synchronized does.

    private val lock: Lock = ReentrantLock()

    fun startDownloadFileAsync(file: File, listener: Listener): Future<*> = bgThreadPoster.post {
        lateinit var file1: DownloadedFile

        // problem : this block of code is synchronized even when the file names are different,
        // this hurts performance by a great extent as the downloading io task also gets synchronized.
        lock.withLock {
            val downloadedFile = getFromCacheOrDownloadFile(file)

            if (downloadedFile != null)
                file1 = downloadedFile
        }

        uiThreadPoster.post {
            listener.onFileResult(file1)
        }
    }

    @WorkerThread
    private fun getFromCacheOrDownloadFile(file: File): DownloadedFile? {
        if (cachedFiles.contains(file.name)) {
            return cachedFiles[file.name]!!
        }

        val downloadedFile = fileDownloader.downloadFileSync(file)

        if (downloadedFile.downloadedFile != null) { // is Result.Success didn't work here!
            cachedFiles[file.name] = downloadedFile.downloadedFile
            return downloadedFile.downloadedFile
        }

        return null // error case
    }
}