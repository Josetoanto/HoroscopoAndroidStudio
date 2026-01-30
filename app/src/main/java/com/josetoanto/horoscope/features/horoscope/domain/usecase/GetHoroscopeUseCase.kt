package com.josetoanto.horoscope.features.horoscope.domain.usecase

import com.josetoanto.horoscope.features.horoscope.domain.model.Horoscope
import com.josetoanto.horoscope.features.horoscope.domain.repository.HoroscopeRepository
import kotlin.Result

class GetHoroscopeUseCase(private val repository: HoroscopeRepository) {
    suspend operator fun invoke(sign: String): Result<Horoscope> {
        return repository.getHoroscope(sign)
    }
}