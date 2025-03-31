package com.kanoyatech.snapdex.domain

import android.content.Context
import android.graphics.Bitmap
import com.kanoyatech.snapdex.data.classifiers.OpenAIClassifier
import com.kanoyatech.snapdex.data.classifiers.TensorflowClassifier
import com.kanoyatech.snapdex.domain.models.PokemonId
import com.kanoyatech.snapdex.domain.repositories.PreferencesRepository

interface Classifier {
    suspend fun init(context: Context)
    suspend fun classify(bitmap: Bitmap): PokemonId?
}

class ClassifierFactory(
    private val preferencesRepository: PreferencesRepository,
    private val openAIClassifier: OpenAIClassifier,
    private val tensorflowClassifier: TensorflowClassifier
): Classifier {
    private var model: AIModel? = null

    override suspend fun init(context: Context) {
        model = preferencesRepository.getAIModel()
        when (model) {
            AIModel.OPENAI -> openAIClassifier.init(context)
            else -> tensorflowClassifier.init(context)
        }
    }

    override suspend fun classify(bitmap: Bitmap): PokemonId? {
        return when (model) {
            AIModel.OPENAI -> openAIClassifier.classify(bitmap)
            else -> tensorflowClassifier.classify(bitmap)
        }
    }
}