package com.saltpay.music.top

import com.saltpay.music.top.domian.model.Album
import com.saltpay.music.top.domian.model.Artist
import com.saltpay.music.top.domian.model.Category
import com.saltpay.music.top.domian.model.Price

object MockModel {
    val simpleEntity = Album(
        "1", Artist("album name", "href"), "name", "imageUrl", 10,
        Price(10f, "usd"), Category(1, "term", "label", "schemeUrl"), null
    )
}