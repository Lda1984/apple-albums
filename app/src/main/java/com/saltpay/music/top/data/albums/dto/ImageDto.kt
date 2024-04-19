package com.saltpay.music.top.data.albums.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageDto(
    @Json(name = "label") val label: String?,
    @Json(name = "attributes") val attributes: Attributes?,
) {
    @JsonClass(generateAdapter = true)
    class Attributes(
        @Json(name = "height") val height: Int,
    )
}
