package com.saltpay.music.top.data.albums.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.math.BigDecimal

@JsonClass(generateAdapter = true)
data class PriceDto(
    @Json(name = "label") val label: String?,
    @Json(name = "attributes") val attributes: Attributes?,
) {
    @JsonClass(generateAdapter = true)
    class Attributes(
        @Json(name = "amount") val amount: Float,
        @Json(name = "currency") val currency: String,
    )
}