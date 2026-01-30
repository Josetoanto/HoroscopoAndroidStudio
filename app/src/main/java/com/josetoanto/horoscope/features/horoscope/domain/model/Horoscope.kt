package com.josetoanto.horoscope.features.horoscope.domain.model

data class Horoscope(
    val date: String,
    val text: String,
    val sign: String
)