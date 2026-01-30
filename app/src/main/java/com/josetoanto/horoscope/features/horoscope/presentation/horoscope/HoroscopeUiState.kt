package com.josetoanto.horoscope.features.horoscope.presentation.horoscope

import com.josetoanto.horoscope.features.horoscope.domain.model.Horoscope

data class HoroscopeUiState(
    val isLoading: Boolean = false,
    val horoscope: Horoscope? = null,
    val error: String? = null
)