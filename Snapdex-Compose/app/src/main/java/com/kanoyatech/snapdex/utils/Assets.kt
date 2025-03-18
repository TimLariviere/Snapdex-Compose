package com.kanoyatech.snapdex.utils

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

object Assets {
    @Throws(IOException::class)
    fun load(context: Context, assetPath: String?): MappedByteBuffer {
        val assetFileDescriptor = context.assets.openFd(assetPath!!)
        val fileInputStream = FileInputStream(assetFileDescriptor.fileDescriptor)
        val fileChannel = fileInputStream.channel

        val startOffset = assetFileDescriptor.startOffset
        val declaredLength = assetFileDescriptor.declaredLength

        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    suspend fun copy(context: Context, sourcePath: Uri, targetFile: File) {
        withContext(Dispatchers.IO) {
            val inputStream = context.contentResolver.openInputStream(sourcePath)
            if (inputStream != null) {
                val outputStream = FileOutputStream(targetFile)
                val buf = ByteArray(1024)
                var len: Int
                while (inputStream.read(buf).also { len = it } > 0) {
                    outputStream.write(buf, 0, len)
                }
                outputStream.close()
            }
            inputStream?.close()
        }
    }
}