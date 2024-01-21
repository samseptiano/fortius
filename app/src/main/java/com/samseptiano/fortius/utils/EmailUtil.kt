package com.samseptiano.fortius.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File

/**
 * Created by samuel.septiano on 25/05/2023.
 */

fun Context.sendToEmail(
    receiver: ArrayList<String>,
    subject: String,
    body: String,
    attachmentFile: ArrayList<File>
) {
    try {
        startActivity(Intent.createChooser(Intent(Intent.ACTION_SEND_MULTIPLE).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_EMAIL, receiver)
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)

            val files = ArrayList<Uri>()

            for (path in attachmentFile) {
                val uri = FileProvider.getUriForFile(
                    this@sendToEmail,
                    applicationContext.packageName + ".provider",
                    path
                )
                files.add(uri)
            }

            putParcelableArrayListExtra(Intent.EXTRA_STREAM, files)
        }, "Send Email"))
    } catch (t: Throwable) {
        Toast.makeText(this, "An error occurred :  ${t.toString()}", Toast.LENGTH_LONG).show()
    }
}
