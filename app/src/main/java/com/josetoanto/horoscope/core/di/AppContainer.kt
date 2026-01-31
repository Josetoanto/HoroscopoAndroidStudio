package com.josetoanto.horoscope.core.di

import android.content.Context
import com.josetoanto.horoscope.BuildConfig
import com.josetoanto.horoscope.core.network.ApiKeyInterceptor
import com.josetoanto.horoscope.core.network.HoroscopeApi
import com.josetoanto.horoscope.features.horoscope.data.repository.HoroscopeRepositoryImpl
import com.josetoanto.horoscope.features.horoscope.domain.repository.HoroscopeRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class AppContainer(context: Context) {

    private val client = OkHttpClient.Builder()
        .addInterceptor(ApiKeyInterceptor(BuildConfig.API_NINJAS_KEY))
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .build()

    private fun <T> createService(serviceClass: Class<T>, baseUrl: String): T {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(serviceClass)
    }

    val horoscopeApi: HoroscopeApi by lazy {
        createService(HoroscopeApi::class.java, BuildConfig.BASE_URL_NINJAS)
    }


    val horoscopeRepository: HoroscopeRepository by lazy {
        HoroscopeRepositoryImpl(horoscopeApi)
    }
}