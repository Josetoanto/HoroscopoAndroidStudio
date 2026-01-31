package com.josetoanto.horoscope.features.horoscope.presentation.horoscope.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josetoanto.horoscope.features.horoscope.domain.usecase.GetHoroscopeUseCase
import com.josetoanto.horoscope.features.horoscope.presentation.horoscope.screen.HoroscopeUiState
import com.josetoanto.horoscope.features.horoscope.presentation.translation.TranslatorHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HoroscopeViewModel(
    private val getHoroscopeUseCase: GetHoroscopeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HoroscopeUiState())
    val uiState = _uiState.asStateFlow()

    fun loadHoroscopeAndTranslate(sign: String, translateToSpanish: Boolean = true) {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null, horoscopeText = null)
        viewModelScope.launch {
            val result = getHoroscopeUseCase(sign)
            result.fold(
                onSuccess = { h ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        rawHoroscopeText = h.text,
                        horoscope = h
                    )

                    if (translateToSpanish) {
                        _uiState.value = _uiState.value.copy(isTranslating = true)
                        try {
                            val translated = TranslatorHelper.translateText(h.text)
                            _uiState.value = _uiState.value.copy(
                                isTranslating = false,
                                horoscopeText = translated
                            )
                        } catch (e: Exception) {
                            _uiState.value = _uiState.value.copy(
                                isTranslating = false,
                                error = "Error al traducir: ${e.message}",
                                horoscopeText = h.text // fallback al original
                            )
                        }
                    } else {
                        _uiState.value = _uiState.value.copy(horoscopeText = h.text)
                    }
                },
                onFailure = { e ->
                    _uiState.value = _uiState.value.copy(isLoading = false, error = e.message ?: "Error")
                }
            )
        }
    }

    // Wrapper para compatibilidad con llamadas previas:
    fun loadHoroscope(sign: String) {
        loadHoroscopeAndTranslate(sign, translateToSpanish = true)
    }
}