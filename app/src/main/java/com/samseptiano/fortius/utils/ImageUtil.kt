package com.samseptiano.fortius.utils

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Environment
import android.util.Base64
import android.util.Log
import com.yalantis.ucrop.UCrop
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


/**
 * Created by samuel.septiano on 23/05/2023.
 */
fun Context.getTempImageUri():Uri{
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val tempCropped = File(cacheDir, "tempImgCropped-${timeStamp}.jpg")
    return Uri.fromFile(tempCropped)
}

fun Activity.callUCropSquare(sourceUri:Uri, destinationUri:Uri){
    UCrop.of(sourceUri, destinationUri)
        .withAspectRatio(1f, 1f)
        .withMaxResultSize(1000, 1000)
        .start(this);
}

fun String.decodeImage(): Bitmap {
    val imageBytes = Base64.decode(this, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
}

fun String.encodeImage(): ByteArray {
    val bm = BitmapFactory.decodeFile(this)
    val baos = ByteArrayOutputStream()
    bm.compress(Bitmap.CompressFormat.JPEG, 75, baos)
    return  baos.toByteArray()
}

fun getFileFromPath(path: String): File?{
    return try{
        if(path.isNotEmpty() && path.isNotBlank()) {
            File(path)
        }
        else{
            null
        }
    }catch (e:Exception){
        null
    }
}

@Throws(IOException::class)
fun convertBitmapToFile(context: Context?, bitmap: Bitmap, _file: File) {
    val isFileCreated = _file.createNewFile()
    if (!isFileCreated) {
        val isFileDeleted = _file.delete()
    }
    if (isFileCreated) {
        val quality = 65
        val targetWidth = 1600
        val intWidth = bitmap.width
        val intHeight = bitmap.height
        val intNewWidth = Math.min(intWidth, targetWidth)
        val intNewHeight: Int = intHeight * intNewWidth / intWidth
        var scaled = Bitmap.createScaledBitmap(bitmap, intNewWidth, intNewHeight, true)
        val bos = ByteArrayOutputStream()
        scaled =
            rotateImage(scaled, 90)
        scaled.compress(Bitmap.CompressFormat.JPEG, quality, bos)
        val bitmapdata = bos.toByteArray()
        val fos = FileOutputStream(_file)
        fos.write(bitmapdata)
        fos.flush()
        fos.close()
    }
}

fun convertBitmapToFile(context: Context, bitmap: Bitmap, filename: String?) {
    val f = File(context.filesDir.absolutePath, filename)
    try {
        f.createNewFile()
        val quality = 65
        val targetWidth = 1600
        val intWidth = bitmap.width
        val intHeight = bitmap.height
        val intNewWidth: Int = if (intWidth > targetWidth) {
            targetWidth
        } else {
            intWidth
        }
        val intNewHeight: Int = intHeight * intNewWidth / intWidth
        var scaled = Bitmap.createScaledBitmap(bitmap, intNewWidth, intNewHeight, true)
        val bos = ByteArrayOutputStream()
        scaled = rotateImage(scaled, 90)
        scaled.compress(Bitmap.CompressFormat.JPEG, quality, bos)
        val bitmapdata = bos.toByteArray()
        val fos = FileOutputStream(f)
        fos.write(bitmapdata)
        fos.flush()
        fos.close()
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
}

fun saveBitmaptoDirectory(bmp: Bitmap, destinationUri: Uri, quality: Int) {
    try {
        FileOutputStream(destinationUri.path).use { out ->
            bmp.compress(
                Bitmap.CompressFormat.JPEG,
                quality,
                out
            )
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

private fun rotateImage(
    img: Bitmap,
    degree: Int
): Bitmap? {
    val matrix = Matrix()
    matrix.postRotate(degree.toFloat())
    return Bitmap.createBitmap(img, 0, 0, img.width, img.height, matrix, true)
}

fun saveUriToFolder(receivedData:Uri, contentResolver: ContentResolver, fileName:String):String{
    try {
        // Get the Downloads directory
        val downloadsDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

        // Get the file name from the URI
//        val fileName = receivedData.path!!.substring(receivedData.path!!.lastIndexOf("/") + 1)

        // Create the destination file in Downloads
        val destinationFile = File(downloadsDir, fileName)

        val inputStream = contentResolver.openInputStream(receivedData)
        val outputStream: OutputStream = FileOutputStream(destinationFile)
        val buffer = ByteArray(4096)
        var bytesRead: Int
        while (inputStream!!.read(buffer).also { bytesRead = it } != -1) {
            outputStream.write(buffer, 0, bytesRead)
        }
        inputStream.close()
        outputStream.close()

        return downloadsDir.path+"/"+fileName


    } catch (e: Exception) {
        // Handle errors
        Log.d("error save to downloaddd", e.toString())

        return ""
    }
}
