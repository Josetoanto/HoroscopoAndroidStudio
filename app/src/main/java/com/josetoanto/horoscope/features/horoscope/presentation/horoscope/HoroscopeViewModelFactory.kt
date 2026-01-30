package com.josetoanto.horoscope.features.horoscope.presentation.horoscope

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.josetoanto.horoscope.features.horoscope.domain.usecase.GetHoroscopeUseCase

class HoroscopeViewModelFactory(
    private val getHoroscopeUseCase: GetHoroscopeUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HoroscopeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HoroscopeViewModel(getHoroscopeUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}