package com.example.androidconcepts.advancedConcurrency.fileDownloader

import com.example.androidconcepts.common.BgThreadPoster
import com.example.androidconcepts.common.UiThreadPosterTD

class FileDownloaderTD : FileDownloader {
    override fun downloadFileSync(file: File): FileDownloader.Result {
        Thread.sleep(500L)
        return FileDownloader.Result.Success(DownloadedFile(file.name, file.url, listOf()))
    }
}