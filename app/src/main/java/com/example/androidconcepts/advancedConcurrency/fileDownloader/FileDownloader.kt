package com.example.androidconcepts.advancedConcurrency.fileDownloader

import androidx.annotation.WorkerThread
import com.example.androidconcepts.advancedConcurrency.fileDownloader.FileDownloader.*

interface FileDownloader {
    @WorkerThread
    fun downloadFileSync(file: File): Result

    sealed class Result(val downloadedFile: DownloadedFile?, val errorMessage: String? = null) {
        class Success(downloadedFile: DownloadedFile) : Result(downloadedFile)
        class Error(errorMessage: String) : Result(null, errorMessage)
    }
}

class FileDownloaderImpl : FileDownloader {
    override fun downloadFileSync(file: File): Result {
        Thread.sleep(500L)

        return Result.Success(
            DownloadedFile(
                name = file.name,
                url = file.url,
                data = listOf()
            )
        )
    }
}