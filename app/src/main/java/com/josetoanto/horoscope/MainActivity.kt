package com.josetoanto.horoscope

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.josetoanto.horoscope.core.di.AppContainer
import com.josetoanto.horoscope.features.horoscope.di.HoroscopeModule
import com.josetoanto.horoscope.features.horoscope.presentation.horoscope.screen.HoroscopeScreen
import com.josetoanto.horoscope.features.horoscope.presentation.horoscope.viewmodel.HoroscopeViewModelFactory

class MainActivity : ComponentActivity() {
    private lateinit var appContainer: AppContainer
    private lateinit var horoscopeModule: HoroscopeModule

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContainer = AppContainer(this)
        horoscopeModule = HoroscopeModule(appContainer)
        setContent {
            val factory: HoroscopeViewModelFactory = horoscopeModule.provideHoroscopeViewModelFactory()
            HoroscopeScreen(factory)
        }
    }
}