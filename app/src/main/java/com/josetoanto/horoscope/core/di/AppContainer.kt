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
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class AppContainer(context: Context) {

    private val okHttp = OkHttpClient.Builder()
        .addInterceptor(ApiKeyInterceptor(BuildConfig.API_NINJAS_KEY))
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.api-ninjas.com/v1/")
        .client(okHttp)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val horoscopeApi: HoroscopeApi by lazy { retrofit.create(HoroscopeApi::class.java) }

    val horoscopeRepository: HoroscopeRepository by lazy {
        HoroscopeRepositoryImpl(horoscopeApi)
    }
}