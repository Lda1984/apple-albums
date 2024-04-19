package com.saltpay.music.top.di

import com.saltpay.music.top.data.albums.AlbumsLocalDataSourceImpl
import com.saltpay.music.top.data.albums.AlbumsRemoteDataSourceImpl
import com.saltpay.music.top.data.albums.AlbumsRepositoryImpl
import com.saltpay.music.top.data.albums.FavoriteRepositoryImpl
import com.saltpay.music.top.domian.AlbumsLocalDataSource
import com.saltpay.music.top.domian.AlbumsRemoteDataSource
import com.saltpay.music.top.domian.AlbumsRepository
import com.saltpay.music.top.domian.FavoriteRepository
import com.saltpay.music.top.utils.DispatcherProvider
import com.saltpay.music.top.utils.DispatcherProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ApplicationBind {

    @Binds
    fun provideDispatcherProvider(repository: DispatcherProviderImpl): DispatcherProvider

    @Binds
    fun provideAlbumsRepository(repository: AlbumsRepositoryImpl): AlbumsRepository

    @Binds
    fun provideAlbumsLocalDataSource(repository: AlbumsLocalDataSourceImpl): AlbumsLocalDataSource

    @Binds
    fun provideAlbumsRemoteDataSource(repository: AlbumsRemoteDataSourceImpl): AlbumsRemoteDataSource

    @Binds
    fun provideFavoriteRepository(repository: FavoriteRepositoryImpl): FavoriteRepository

}