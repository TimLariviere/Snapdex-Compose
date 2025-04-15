package com.kanoyatech.snapdex.data.classifiers

import android.content.Context
import android.util.Log
import com.kanoyatech.snapdex.data.utils.Assets
import com.kanoyatech.snapdex.domain.Classifier
import com.kanoyatech.snapdex.domain.models.PokemonId
import java.nio.ByteBuffer
import org.tensorflow.lite.Interpreter

class TensorflowClassifier(private val context: Context) : Classifier {
    private lateinit var interpreter: Interpreter

    override suspend fun init() {
        val model = Assets.load(context, "model.tflite")
        val options = Interpreter.Options()
        interpreter = Interpreter(model, options)
    }

    override suspend fun classify(bitmap: ByteBuffer): PokemonId? {
        val outputArray = Array(1) { FloatArray(149) }

        interpreter.run(bitmap, outputArray)
        val floatArray = outputArray[0]
        val pokemonIds =
            floatArray.indices
                .sortedByDescending { floatArray[it] }
                .take(5)
                .filter { floatArray[it] > 0.002 }

        pokemonIds.forEachIndexed { index, id ->
            val percentage = floatArray[id] * 100.0
            Log.i("Found", "Top ${index + 1} - Pokemon #${id} = ${percentage}%")
        }

        val pokemonId = pokemonIds.firstOrNull()

        return if (pokemonId != null) pokemonId + 1 else null
    }
}
