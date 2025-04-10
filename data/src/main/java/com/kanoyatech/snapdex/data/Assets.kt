package com.kanoyatech.snapdex.data

import android.content.Context
import java.io.FileInputStream
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
}
