package com.example.androidconcepts.advancedConcurrency.fileDownloader

import com.example.androidconcepts.common.BgThreadPoster
import com.example.androidconcepts.common.UiThreadPoster
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

    fun startDownloadFileAsync(file: File, listener: Listener) = bgThreadPoster.post {
        lateinit var downloadedFile1 : DownloadedFile

        lock.withLock {
            downloadedFile1 = startDownloadSync(file)
        }

        uiThreadPoster.post {
            listener.onFileResult(downloadedFile1)
        }
    }

    fun startDownloadSync(file: File): DownloadedFile {
        if (cachedFiles.contains(file.name)) {
            return cachedFiles[file.name]!!
        }

        val downloadedFile = fileDownloader.downloadFileSync(file)
        cachedFiles[file.name] = downloadedFile
        return downloadedFile
    }
}