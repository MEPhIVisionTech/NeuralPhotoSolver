package ru.tanexc.photosolver.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.net.Uri
import android.provider.OpenableColumns
import androidx.core.net.toUri
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


object ContextUtils {

    fun Context.findActivity(): Activity? = when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> null
    }

    fun Context.getFile(uri: String?): File? {
        if (uri?.isEmpty() == true) return null

        fun Context.queryName(uri: Uri?): String {
            if (uri?.toString()?.isEmpty() == true) return ""

            return contentResolver.query(uri!!, null, null, null, null)?.run {
                getString(moveToFirst().run { getColumnIndex(OpenableColumns.DISPLAY_NAME) }).also { close() }
            } ?: ""
        }

        fun createFileFromStream(ins: InputStream, destination: File?) {
            runCatching {
                FileOutputStream(destination).use { os ->
                    val buffer = ByteArray(4096)
                    var length: Int
                    while (ins.read(buffer).also { length = it } > 0) {
                        os.write(buffer, 0, length)
                    }
                    os.flush()
                }
            }
        }

        return File("${filesDir.path}${File.separatorChar}${queryName(uri?.toUri())}").apply {
            contentResolver.openInputStream(uri!!.toUri())?.use { ins ->
                createFileFromStream(ins, this)
            }
        }
    }
}