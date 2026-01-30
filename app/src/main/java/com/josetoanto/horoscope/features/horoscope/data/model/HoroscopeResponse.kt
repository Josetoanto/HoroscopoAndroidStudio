package com.josetoanto.horoscope.features.horoscope.data.model

import com.google.gson.annotations.SerializedName

data class HoroscopeResponse(
    @SerializedName("date") val date: String?,
    @SerializedName("horoscope") val horoscope: String?,
    @SerializedName("sunsign") val sunsign: String?
)