package com.kanoyatech.snapdex.services

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.kanoyatech.snapdex.domain.PokemonId
import com.kanoyatech.snapdex.utils.AssetsLoader
import org.tensorflow.lite.Interpreter
import java.nio.ByteBuffer
import java.nio.ByteOrder

class PokemonClassifier {
    private lateinit var interpreter: Interpreter

    fun init(context: Context) {
        val model = AssetsLoader.load(context, "model.tflite")
        val options = Interpreter.Options()
        interpreter = Interpreter(model, options)
    }

    fun classify(bitmap: Bitmap): PokemonId? {
        val inputBuffer = preprocessBitmap(bitmap)
        val outputArray = Array(1) { FloatArray(149) }

        interpreter.run(inputBuffer, outputArray)
        val floatArray = outputArray[0]
        val pokemonIds = floatArray.indices

        pokemonIds.forEach {
            val percentage = floatArray[it] * 100.0
            val p = if (percentage > 5.0) percentage else 0.0
            Log.i("Found", "Pokemon #${it} = ${p}%")
        }

        val pokemonId = pokemonIds.maxByOrNull { floatArray[it] }

        return if (pokemonId != null) pokemonId + 1 else null
    }

    private fun preprocessBitmap(bitmap: Bitmap): ByteBuffer {
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
        val scaledBitmap = Bitmap.createScaledBitmap(croppedBitmap, modelInputSize, modelInputSize, true)

        val byteBuffer = ByteBuffer.allocateDirect(4 * modelInputSize * modelInputSize * 3) // 3 for RGB
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