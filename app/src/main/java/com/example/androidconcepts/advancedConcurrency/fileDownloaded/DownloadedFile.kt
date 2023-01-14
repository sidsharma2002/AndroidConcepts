package com.example.androidconcepts.advancedConcurrency.fileDownloaded

data class DownloadedFile(
    val name: String,
    val url: String,
    val data: List<Byte>
)
