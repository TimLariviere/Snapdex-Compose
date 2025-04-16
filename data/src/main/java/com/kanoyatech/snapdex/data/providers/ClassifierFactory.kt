package com.kanoyatech.snapdex.data.providers

import com.kanoyatech.snapdex.data.preferences.DataPreferencesStore
import com.kanoyatech.snapdex.domain.models.AIModel
import com.kanoyatech.snapdex.domain.models.PokemonId
import com.kanoyatech.snapdex.domain.providers.Classifier
import java.nio.ByteBuffer

class ClassifierFactory(
    private val dataPreferencesStore: DataPreferencesStore,
    private val openAIClassifier: OpenAIClassifier,
    private val tensorflowClassifier: TensorflowClassifier,
) : Classifier {
    private var model: AIModel? = null

    override suspend fun init() {
        model = dataPreferencesStore.getAIModel()
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
