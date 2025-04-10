package com.kanoyatech.snapdex.data.classifiers

import com.kanoyatech.snapdex.domain.AIModel
import com.kanoyatech.snapdex.domain.Classifier
import com.kanoyatech.snapdex.domain.models.PokemonId
import com.kanoyatech.snapdex.domain.repositories.PreferencesRepository
import java.nio.ByteBuffer

class ClassifierFactory(
    private val preferencesRepository: PreferencesRepository,
    private val openAIClassifier: OpenAIClassifier,
    private val tensorflowClassifier: TensorflowClassifier,
) : Classifier {
    private var model: AIModel? = null

    override suspend fun init() {
        model = preferencesRepository.getAIModel()
        when (model) {
            AIModel.OPENAI -> openAIClassifier.init()
            else -> tensorflowClassifier.init()
        }
    }

    override suspend fun classify(bitmap: ByteBuffer): PokemonId? {
        return when (model) {
            AIModel.OPENAI -> openAIClassifier.classify(bitmap)
            else -> tensorflowClassifier.classify(bitmap)
        }
    }
}
