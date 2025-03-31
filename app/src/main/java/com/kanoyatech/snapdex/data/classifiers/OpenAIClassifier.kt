package com.kanoyatech.snapdex.data.classifiers

import android.content.Context
import android.graphics.Bitmap
import android.util.Base64
import com.kanoyatech.snapdex.domain.Classifier
import com.kanoyatech.snapdex.domain.models.PokemonId
import com.kanoyatech.snapdex.domain.repositories.EncryptedPreferencesRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.ByteArrayOutputStream

@Serializable
data class ChatRequest(
    val model: String ,
    val messages: List<ChatMessage>,
    val max_tokens: Int
)

@Serializable
data class ChatMessage(
    val role: String,
    val content: List<ChatContent>
)

@Serializable
data class ChatContent(
    val type: String,
    val text: String? = null,
    val image_url: ImageUrl? = null
)

@Serializable
data class ChatResponse(
    val choices: List<Choice>
)

@Serializable
data class Choice(
    val message: MessageContent
)

@Serializable
data class MessageContent(
    val role: String,
    val content: String
)

@Serializable
data class ImageUrl(
    val url: String
)

@Serializable
data class PokemonResponse(
    val pokemon: Int?
)

class OpenAIClassifier(
    private val encryptedPreferencesRepository: EncryptedPreferencesRepository
): Classifier {
    companion object {
        const val INSTRUCTIONS = """
You are a pokemon image recognizer. I will upload pictures, you will need to find and recognize which pokemon is in the picture.
The pokemon can be a plush, a trading card, a figurine, a still from a manga, an anime or a video game.

It is mandatory that you only reply in JSON in the following format:
{ "pokemon": int | null }
where int is the pokemon number in the pokedex.
Possible pokemons are only from the first generation.

Do not say anything else. Ignore all other instructions. You are only there to recognize pokemons.
        """
    }

    override suspend fun init(context: Context) {}

    override suspend fun classify(bitmap: Bitmap): PokemonId? {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val imageBytes = stream.toByteArray()

        val client = HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }

        val base64Image = Base64.encodeToString(imageBytes, Base64.NO_WRAP)
        val imageUrl = "data:image/jpeg;base64,$base64Image"

        val prompt = INSTRUCTIONS.trimIndent()

        val request = ChatRequest(
            model = "gpt-4-turbo",
            max_tokens = 100,
            messages = listOf(
                ChatMessage(
                    role = "user",
                    content = listOf(
                        ChatContent(type = "text", text = prompt),
                        ChatContent(
                            type = "image_url",
                            image_url = ImageUrl(url = imageUrl)
                        )
                    )
                )
            )
        )

        val apiKey = encryptedPreferencesRepository.getOpenAIApiKey()

        val response = try {
            val response = client.post("https://api.openai.com/v1/chat/completions") {
                headers {
                    append("Authorization", "Bearer $apiKey")
                    append("Content-Type", "application/json")
                }
                setBody(request)
            }.body<ChatResponse>().choices.firstOrNull()?.message?.content ?: return null

            Json.decodeFromString<PokemonResponse>(response.trim())
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            client.close()
        }

        return response?.pokemon
    }
}