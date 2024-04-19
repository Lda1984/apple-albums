package com.saltpay.music.top.data.albums.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CountDto(
    @Json(name = "label") val count: Int = 0,
)
