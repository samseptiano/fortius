package com.samseptiano.fortius.utils

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import android.webkit.MimeTypeMap
import android.webkit.URLUtil
import androidx.core.content.FileProvider
import com.samseptiano.fortius.BuildConfig
import com.samseptiano.fortius.R
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

/**
 * Created by samuel.septiano on 05/05/2023.
 */

 fun Context.startDownloadFile(url: String?) {
    val fileName = URLUtil.guessFileName(url, null, MimeTypeMap.getFileExtensionFromUrl(url))
    val uri = Uri.parse(url)
    val request = DownloadManager.Request(uri)
    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
    request.setTitle(fileName)
    request.setDescription(getString(R.string.file_is_downloading))
    request.allowScanningByMediaScanner()
    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
    request.setDestinationInExternalPublicDir(
        Environment.DIRECTORY_DOWNLOADS,
        uri.lastPathSegment
    )
    val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    manager.enqueue(request)
}

fun enableStrictMode(){
    val builder = VmPolicy.Builder()
    StrictMode.setVmPolicy(builder.build())
}

fun Context.getTmpFileUri(): Uri {
    val tmpFile = File.createTempFile("tmp_image_file", ".png", cacheDir).apply {
        createNewFile()
        deleteOnExit()
    }
    return FileProvider.getUriForFile(applicationContext, "${BuildConfig.APPLICATION_ID}.provider", tmpFile)
}

@Throws(IOException::class)
fun convertFileToByteArray(file: File): ByteArray? {
    val outputStream = ByteArrayOutputStream()
    val inputStream = FileInputStream(file)
    val buffer = ByteArray(1024)
    var bytesRead: Int
    while (inputStream.read(buffer).also { bytesRead = it } != -1) {
        outputStream.write(buffer, 0, bytesRead)
    }
    inputStream.close()
    return outputStream.toByteArray()
}

@Throws(IOException::class)
private fun convertByteArrayToFile(byteArray: ByteArray, filePath: String): File {
    val file = File(filePath)
    val fileOutputStream = FileOutputStream(file)
    fileOutputStream.write(byteArray)
    fileOutputStream.close()
    return file
}

