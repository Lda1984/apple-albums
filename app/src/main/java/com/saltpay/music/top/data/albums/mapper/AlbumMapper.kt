package com.saltpay.music.top.data.albums.mapper

import com.saltpay.music.top.data.albums.dto.EntryDto
import com.saltpay.music.top.domian.model.Album
import com.saltpay.music.top.domian.model.Artist
import com.saltpay.music.top.domian.model.Category
import com.saltpay.music.top.domian.model.Price
import javax.inject.Inject

class AlbumMapper @Inject constructor() {

    fun fromDtoToDomain(dto: EntryDto): Album {
        with(dto){
            val artist = Artist(artist.label, artist.attributes?.href)
            val category = with(category.attributes) {
                Category(id, term, label, scheme)
            }
            val image = images.maxBy { it.attributes?.height ?: 0 }
            val price = this.price.attributes!!.let {
                Price(it.amount, it.currency)
            }
            return Album(
                id.attributes.id,
                artist,
                name.label,
                image.label ?: "",
                itemCount.count,
                price,
                category,
                link.attributes?.href ?: ""
            )
        }

    }
}
