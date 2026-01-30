package com.josetoanto.horoscope.features.horoscope.data.mapper

import com.josetoanto.horoscope.features.horoscope.data.model.HoroscopeResponse
import com.josetoanto.horoscope.features.horoscope.domain.model.Horoscope

fun HoroscopeResponse.toDomain(): Horoscope {
    return Horoscope(
        date = this.date.orEmpty(),
        text = this.horoscope.orEmpty(),
        sign = this.sunsign.orEmpty()
    )
}