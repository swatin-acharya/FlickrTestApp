package com.example.flickrtestapp.di

import com.example.flickrtestapp.network.Api
import com.example.flickrtestapp.network.AppNetwork
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/*Hilt Dagger Provider objects for dependency injection of Retrofit instance*/
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    internal fun provideRetrofitInstance():Retrofit {
        return AppNetwork.getInstance()
    }

    @Provides
    @Singleton
    internal fun provideApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }
}