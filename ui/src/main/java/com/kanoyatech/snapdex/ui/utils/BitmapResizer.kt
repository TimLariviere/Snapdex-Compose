package com.kanoyatech.snapdex.ui.utils

import android.graphics.Bitmap
import androidx.core.graphics.scale
import java.nio.ByteBuffer
import java.nio.ByteOrder

object BitmapResizer {
    fun resize(bitmap: Bitmap): ByteBuffer {
        val modelInputSize = 224

        // Get the original width and height
        val width = bitmap.width
        val height = bitmap.height

        // Determine the square crop size
        val squareSize = minOf(width, height)

        // Calculate the starting points to center-crop
        val xOffset = (width - squareSize) / 2
        val yOffset = (height - squareSize) / 2

        // Crop the bitmap to a square
        val croppedBitmap = Bitmap.createBitmap(bitmap, xOffset, yOffset, squareSize, squareSize)

        // Scale the cropped bitmap to the required size
        val scaledBitmap = croppedBitmap.scale(modelInputSize, modelInputSize)

        val byteBuffer =
            ByteBuffer.allocateDirect(4 * modelInputSize * modelInputSize * 3) // 3 for RGB
        byteBuffer.order(ByteOrder.nativeOrder())

        val intValues = IntArray(modelInputSize * modelInputSize)
        scaledBitmap.getPixels(intValues, 0, modelInputSize, 0, 0, modelInputSize, modelInputSize)

        for (pixel in intValues) {
            val r = (pixel shr 16 and 0xFF) / 255.0f
            val g = (pixel shr 8 and 0xFF) / 255.0f
            val b = (pixel and 0xFF) / 255.0f

            byteBuffer.putFloat(r)
            byteBuffer.putFloat(g)
            byteBuffer.putFloat(b)
        }
        return byteBuffer
    }
}
