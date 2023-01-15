package com.example.androidconcepts.advancedConcurrency.fileDownloader

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
        hashMapOf() // concurrent hashmap doesn't work here for some reason.

    private val lock: Lock = ReentrantLock()

    fun startDownloadFileAsync(file: File, listener: Listener): Future<*> = bgThreadPoster.post {
        lateinit var file1 : DownloadedFile

        // problem : this block of code is synchronized even when the file names are different,
        // this hurts performance by a great extent as the downloading io task also gets synchronized.
        lock.withLock {
            file1 = getFromCacheOrDownloadFile(file)
        }

        uiThreadPoster.post {
            listener.onFileResult(file1)
        }
    }

    private fun getFromCacheOrDownloadFile(file: File): DownloadedFile {
        if (cachedFiles.contains(file.name)) {
            return cachedFiles[file.name]!!
        }

        val downloadedFile = fileDownloader.downloadFileSync(file)
        cachedFiles[file.name] = downloadedFile
        return downloadedFile
    }
}