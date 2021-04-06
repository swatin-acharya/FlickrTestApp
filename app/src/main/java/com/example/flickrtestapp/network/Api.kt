package com.example.flickrtestapp.network

import com.example.flickrtestapp.models.Wrapper
import retrofit2.http.GET
import retrofit2.http.Query

/*Retrofit API calls go here*/
interface Api {

    @GET("?method=flickr.people.getPublicPhotos&extras=url_m%2Cowner_name&format=json&nojsoncallback=1")
    suspend fun getFlickrData(
        @Query("api_key") apiKey: String,
        @Query("user_id") userId: String): Wrapper
}