package com.example.androidconcepts.advancedConcurrency.fileDownloader

interface FileDownloader {
    fun downloadFileSync(file: File): DownloadedFile
}

class FileDownloaderImpl : FileDownloader {
    override fun downloadFileSync(file: File): DownloadedFile {
        Thread.sleep(500L)
        return DownloadedFile(
            name = file.name,
            url = file.url,
            data = listOf()
        )
    }
}