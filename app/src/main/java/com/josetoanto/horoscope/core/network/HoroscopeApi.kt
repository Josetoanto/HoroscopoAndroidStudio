package com.josetoanto.horoscope.core.network

import com.josetoanto.horoscope.features.horoscope.data.model.HoroscopeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface HoroscopeApi {
    @GET("horoscope")
    suspend fun getHoroscope(@Query("zodiac") sign: String): HoroscopeResponse
}