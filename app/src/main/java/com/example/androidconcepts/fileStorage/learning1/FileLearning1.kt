package com.example.androidconcepts.fileStorage.learning1

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import androidx.core.text.isDigitsOnly
import java.io.File

class FileLearning1 constructor(private val context: Context) {

    private val myDigitsFileName = "myCreatedFile"

    fun start() {
        logBasicDetails()

        Log.d("FILE","-------------------------")

        incrementAppLaunchCounterInFile()
    }

    private fun incrementAppLaunchCounterInFile() {
        checkIfFileExistsElseCreate()

        Log.d("FILE","-------------------------")

        var readData = readAndGetDataFromFile(myDigitsFileName)

        if (!readData.isDigitsOnly())
            readData = "0"

        val newCount = readData.toInt() + 1

        writeNewDataToFile(newCount)

        Log.d("FILE", "data : $readData")
    }

    private fun checkIfFileExistsElseCreate() {
        // though in input/output stream we directly pass the name to access from filesDir,
        // here we need to explicitly mention the path.
        val file = File(context.filesDir.path + File.separator + myDigitsFileName)

        if (!file.exists()) {
            Log.d("FILE", "created new file path : " + file.path)
        } else {
            Log.d("FILE", "already exists : " + file.path)
        }
    }

    private fun readAndGetDataFromFile(filesDirName: String): String {
        var readData = ""

        // any file accessed like this (by passing direct name) will be accessed from files directory of the app.
        context.openFileInput(filesDirName)
            .use {
                while (true) {
                    val data = it.read()
                    if (data == -1) break
                    readData += data.toChar().toString()
                }
            }

        return readData
    }

    private fun writeNewDataToFile(newCount: Int) {
        // any file accessed like this (by passing direct name) will be accessed from files directory of the app.
        context.openFileOutput(myDigitsFileName, MODE_PRIVATE).use {
            it.write(newCount.toString().toByteArray())
        }
    }

    private fun logBasicDetails() {

        context.fileList().forEach {
            val file = File(context.filesDir.path + File.separator + it)
            Log.d("FILE", "file details : " + file.path)
        }

        Log.d("FILE","-------------------------")

        printDetails(
            prefix = "cacheDir",
            file = context.cacheDir
        ) // cacheDir path /data/user/0/com.example.androidconcepts/cache name : cache isDir : true

        printDetails(
            prefix = "filesDir",
            file = context.filesDir
        ) // filesDir path /data/user/0/com.example.androidconcepts/files name : files isDir : true

        if (context.externalCacheDir != null)
            printDetails(
                prefix = "externalDir",
                file = context.externalCacheDir!!
            ) // externalDir path /storage/emulated/0/Android/data/com.example.androidconcepts/cache name : cache isDir : true
    }

    private fun printDetails(prefix: String, file: File): String {
        val string =
            "$prefix path " + file.path + " name : " + file.name + " isDir : " + file.isDirectory

        Log.d("FILE", string)

        return string
    }

}