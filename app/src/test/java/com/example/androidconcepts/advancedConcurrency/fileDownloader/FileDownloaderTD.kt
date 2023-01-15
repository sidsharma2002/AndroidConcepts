package com.example.androidconcepts.advancedConcurrency.fileDownloader

import com.example.androidconcepts.common.BgThreadPoster
import com.example.androidconcepts.common.UiThreadPosterTD

class FileDownloaderTD : FileDownloader {
    override fun downloadFileSync(file: File): DownloadedFile {
        Thread.sleep(500L)
        return DownloadedFile(file.name, file.url, listOf())
    }
}