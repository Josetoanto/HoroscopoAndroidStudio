package com.josetoanto.horoscope.features.horoscope.domain.repository

import com.josetoanto.horoscope.features.horoscope.domain.model.Horoscope

interface HoroscopeRepository {
    suspend fun getHoroscope(sign: String): Result<Horoscope>
}