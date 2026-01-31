package com.josetoanto.horoscope.features.horoscope.presentation.translation

import android.util.Log
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.languageid.LanguageIdentification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

object TranslatorHelper {
    private const val TAG = "TranslatorHelper"

    private fun createTranslator(sourceLang: String, targetLang: String): Translator {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(sourceLang)
            .setTargetLanguage(targetLang)
            .build()
        return Translation.getClient(options)
    }

    private suspend fun detectLanguage(text: String): String? = withContext(Dispatchers.IO) {
        suspendCancellableCoroutine { cont ->
            val identifier = LanguageIdentification.getClient()
            identifier.identifyLanguage(text)
                .addOnSuccessListener { languageCode ->
                    // "und" = undetermined
                    if (languageCode != "und") {
                        if (!cont.isCompleted) cont.resume(languageCode)
                    } else {
                        if (!cont.isCompleted) cont.resume(null)
                    }
                }
                .addOnFailureListener { ex ->
                    Log.w(TAG, "Error identificando idioma: ${ex.message}", ex)
                    if (!cont.isCompleted) cont.resume(null)
                }
        }
    }


    suspend fun translateText(
        text: String,
        sourceLang: String? = null,
        targetLang: String = TranslateLanguage.SPANISH
    ): String = withContext(Dispatchers.IO) {
        if (text.isBlank()) return@withContext text

        val srcLang = sourceLang ?: detectLanguage(text) ?: TranslateLanguage.ENGLISH
        Log.d(TAG, "Idioma fuente: $srcLang -> objetivo: $targetLang")

        if (srcLang == targetLang) {
            Log.d(TAG, "Texto ya en idioma objetivo, no se traduce")
            return@withContext text
        }

        val translator = createTranslator(srcLang, targetLang)
        try {
            Log.d(TAG, "Preparando descarga del modelo de traducción $srcLang -> $targetLang")
            val conditions = DownloadConditions.Builder().build()

            suspendCancellableCoroutine<Unit> { cont ->
                translator.downloadModelIfNeeded(conditions)
                    .addOnSuccessListener {
                        if (!cont.isCompleted) cont.resume(Unit)
                    }
                    .addOnFailureListener { ex ->
                        if (!cont.isCompleted) cont.resumeWithException(ex)
                    }
            }

            Log.d(TAG, "Iniciando traducción del texto (length=${text.length})")
            val translated = suspendCancellableCoroutine<String> { cont ->
                translator.translate(text)
                    .addOnSuccessListener { result ->
                        if (!cont.isCompleted) cont.resume(result)
                    }
                    .addOnFailureListener { ex ->
                        if (!cont.isCompleted) cont.resumeWithException(ex)
                    }
            }

            translated
        } finally {
            try {
                translator.close()
                Log.d(TAG, "Translator cerrado")
            } catch (e: Exception) {
                Log.w(TAG, "Error al cerrar translator: ${e.message}")
            }
        }
    }
}