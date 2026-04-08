package com.gym.core.translation

import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TranslatorManager @Inject constructor() {

    private var englishVietnameseTranslator: Translator? = null
    var isModelDownloaded: Boolean = false
        private set

    init {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.VIETNAMESE)
            .build()
        englishVietnameseTranslator = Translation.getClient(options)
    }

    suspend fun downloadModelIfNeeded(): Boolean {
        return try {
            val conditions = DownloadConditions.Builder()
                // .requireWifi() // Optional: Bỏ comment nếu muốn ép tải qua Wifi
                .build()
            
            englishVietnameseTranslator?.downloadModelIfNeeded(conditions)?.await()
            isModelDownloaded = true
            true
        } catch (e: Exception) {
            e.printStackTrace()
            isModelDownloaded = false
            false
        }
    }

    suspend fun translate(text: String): String {
        if (text.isBlank()) return text
        if (!isModelDownloaded) {
            // Nếu chưa tải xong model thì fallback về nguyên gốc tiếng Anh
            return text
        }
        return try {
            englishVietnameseTranslator?.translate(text)?.await() ?: text
        } catch (e: Exception) {
            e.printStackTrace()
            text
        }
    }

    suspend fun translateList(texts: List<String>): List<String> {
        if (texts.isEmpty()) return texts
        if (!isModelDownloaded) return texts
        return texts.map { translate(it) }
    }
}
