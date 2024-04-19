package com.saltpay.music.top.data.albums

import com.saltpay.music.top.data.albums.dto.AlbumsResponse
import retrofit2.Response
import retrofit2.http.GET
import java.io.IOException

interface ApiAlbums {
    companion object{
        const val BASE_URL = "https://itunes.apple.com/us/rss/"
    }

    @GET("topalbums/limit=100/json")
    suspend fun getTopAlbums() : Response<AlbumsResponse>
}

fun <T> Response<T>.dematerialize(): T {
    if (isSuccessful) {
        return body()!!
    } else {
        throw IOException()
    }
}