package com.example.androidconcepts.advancedConcurrency.fileDownloaded

import com.example.androidconcepts.common.BgThreadPoster
import com.example.androidconcepts.common.UiThreadPoster

interface FileDownloader {
    interface Listener {
        fun onFileDownloaded(downloadedFile: DownloadedFile)
    }

    fun downloadFileAsync(file: File, listener: Listener)
}

class FileDownloaderImpl constructor(
    private val bgThreadPoster: BgThreadPoster = BgThreadPoster(),
    private val uiThreadPoster: UiThreadPoster = UiThreadPoster()
) : FileDownloader {
    override fun downloadFileAsync(file: File, listener: FileDownloader.Listener) {
        bgThreadPoster.post {
            Thread.sleep(500L)

            uiThreadPoster.post {
                listener.onFileDownloaded(
                    DownloadedFile(
                        name = file.name,
                        url = file.url,
                        data = listOf()
                    )
                )
            }
        }
    }
}