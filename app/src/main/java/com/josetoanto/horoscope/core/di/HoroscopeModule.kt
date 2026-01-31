package com.josetoanto.horoscope.features.horoscope.di

import com.josetoanto.horoscope.core.di.AppContainer
import com.josetoanto.horoscope.features.horoscope.domain.usecase.GetHoroscopeUseCase
import com.josetoanto.horoscope.features.horoscope.presentation.horoscope.viewmodel.HoroscopeViewModelFactory

class HoroscopeModule(private val appContainer: AppContainer) {
    private fun provideGetHoroscopeUseCase(): GetHoroscopeUseCase {
        return GetHoroscopeUseCase(appContainer.horoscopeRepository)
    }

    fun provideHoroscopeViewModelFactory(): HoroscopeViewModelFactory {
        return HoroscopeViewModelFactory(provideGetHoroscopeUseCase())
    }
}
