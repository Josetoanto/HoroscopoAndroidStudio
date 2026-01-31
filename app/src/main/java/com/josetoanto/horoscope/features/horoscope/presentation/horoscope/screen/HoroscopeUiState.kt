package com.josetoanto.horoscope.features.horoscope.presentation.horoscope.screen

import com.josetoanto.horoscope.features.horoscope.domain.model.Horoscope

data class HoroscopeUiState(
    val isLoading: Boolean = false,
    val isTranslating: Boolean = false,
    val horoscopeText: String? = null,
    val rawHoroscopeText: String? = null,
    val horoscope: Horoscope? = null,
    val error: String? = null
)