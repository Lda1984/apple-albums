package com.saltpay.music.top.data.albums.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class IdDto(
    @Json(name = "label") val label: String?,
    @Json(name = "attributes") val attributes: Attributes,
) {
    @JsonClass(generateAdapter = true)
    class Attributes(
        @Json(name = "im:id") val id: String,
    )
}
