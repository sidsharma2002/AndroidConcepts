package com.example.androidconcepts.advancedConcurrency.fileDownloader

import androidx.annotation.WorkerThread
import com.example.androidconcepts.common.BgThreadPoster
import com.example.androidconcepts.common.UiThreadPoster
import java.util.concurrent.Future

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

    private val MONITOR = Any()

    private val currentlyDownloadingFiles = mutableListOf<String>()

    fun startDownloadFileAsync(file: File, listener: Listener): Future<*> = bgThreadPoster.post {
        waitIfSameFileIsDownloading(file.name)

        currentlyDownloadingFiles.add(file.name)
        val downloadedFile = getFromCacheOrDownloadFile(file)
        currentlyDownloadingFiles.remove(file.name)

        notifyAllMonitors()

        if (downloadedFile != null)
            uiThreadPoster.post {
                listener.onFileResult(downloadedFile)
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

    private fun notifyAllMonitors() {
        synchronized(MONITOR) {
            (MONITOR as Object).notifyAll()
        }
    }

    private fun waitIfSameFileIsDownloading(fileName: String) {
        synchronized(MONITOR) {
            while (currentlyDownloadingFiles.contains(fileName)) {
                (MONITOR as Object).wait()
            }
        }
    }
}