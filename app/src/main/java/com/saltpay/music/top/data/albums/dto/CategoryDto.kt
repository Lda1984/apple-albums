package com.saltpay.music.top.data.albums.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoryDto(
    @Json(name = "attributes") val attributes: Attributes,
) {
    @JsonClass(generateAdapter = true)
    class Attributes(
        @Json(name = "im:id") val id: Int,
        @Json(name = "term") val term: String,
        @Json(name = "scheme") val scheme: String,
        @Json(name = "label") val label: String,
    )
}