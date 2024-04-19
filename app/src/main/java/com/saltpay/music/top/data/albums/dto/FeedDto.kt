package com.saltpay.music.top.data.albums.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Response object for a feedDto. Sample data:
 *     https://itunes.apple.com/us/rss/topalbums/limit=100/json
 */
@JsonClass(generateAdapter = true)
data class FeedDto(
    @Json(name = "entry") val entry: List<EntryDto> = emptyList(),
)

