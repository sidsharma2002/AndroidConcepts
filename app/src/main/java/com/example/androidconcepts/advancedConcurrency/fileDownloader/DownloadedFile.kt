package com.example.androidconcepts.advancedConcurrency.fileDownloader

data class DownloadedFile(
    val name: String,
    val url: String,
    val data: List<Byte>
)
