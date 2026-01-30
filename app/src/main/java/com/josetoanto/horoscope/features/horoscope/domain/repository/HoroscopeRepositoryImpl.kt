package com.josetoanto.horoscope.features.horoscope.data.repository

import com.josetoanto.horoscope.core.network.HoroscopeApi
import com.josetoanto.horoscope.features.horoscope.data.mapper.toDomain
import com.josetoanto.horoscope.features.horoscope.domain.model.Horoscope
import com.josetoanto.horoscope.features.horoscope.domain.repository.HoroscopeRepository
import kotlin.Result

class HoroscopeRepositoryImpl(
    private val api: HoroscopeApi
) : HoroscopeRepository {
    override suspend fun getHoroscope(sign: String): Result<Horoscope> {
        return try {
            val response = api.getHoroscope(sign)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}