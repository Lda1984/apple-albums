package com.saltpay.music.top.domian.model

data class Album(
    val id: String,
    val artist: Artist,
    val name: String,
    val imageUrl: String,
    val itemCount: Int,
    val price: Price,
    val category: Category,
    val href: String?,
    val isFavorite: Boolean = false
)
