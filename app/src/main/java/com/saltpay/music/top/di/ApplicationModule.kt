package com.saltpay.music.top.di

import android.util.Log
import com.saltpay.music.top.BuildConfig
import com.saltpay.music.top.data.albums.ApiAlbums
import com.saltpay.music.top.data.albums.ApiAlbums.Companion.BASE_URL
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    private fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor { message ->
            Log.d("Retrofit logging", message)
        }.apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    private fun provideLoggingCapableHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    internal fun provideApiAlbums(
        moshi: Moshi,
    ): ApiAlbums {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)

            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(provideLoggingCapableHttpClient(provideLoggingInterceptor()))
            .build()
            .create(ApiAlbums::class.java)

    }
}
