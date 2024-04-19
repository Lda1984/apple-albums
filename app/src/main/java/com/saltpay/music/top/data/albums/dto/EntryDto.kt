package com.saltpay.music.top.data.albums.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EntryDto(
    @Json(name = "im:name") val name: LabelDto,
    @Json(name = "im:image") val images: List<ImageDto>,
    @Json(name = "im:itemCount") val itemCount: CountDto,
    @Json(name = "im:price") val price: PriceDto,
    @Json(name = "right") val right: LabelDto = LabelDto(""),
    @Json(name = "title") val title: LabelDto,
    @Json(name = "link") val link: LinkDto,
    @Json(name = "id") val id: IdDto,
    @Json(name = "im:artist") val artist: ArtistDto,
    @Json(name = "category") val category: CategoryDto,
    @Json(name = "im:releaseDate") val releaseDate: LabelDto
)