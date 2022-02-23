package com.example.myapplication.quiz2.service

import com.example.myapplication.quiz2.photo.FlickrResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MyService {

    @GET("services/rest")
    fun get(
        @Query("api_key") api_key: String = "1c04e05bce6e626247758d120b372a73",
        @Query("method") method: String = "flickr.photos.getPopular",
        @Query("user_id") user_id: String = "34427466731@N01",
        @Query("extras") extras: String = "url_s",
        @Query("format") format: String = "json",
        @Query("nojsoncallback") nojsoncallback: Int = 1,
        @Query("per_page") per_page: Int = 100,
        @Query("page") page: Int = 1
    ) : Call<FlickrResponse>

    @GET("services/rest")
    fun search(
        @Query("api_key") api_key: String = "1c04e05bce6e626247758d120b372a73",
        @Query("method") method: String = "flickr.photos.search",
        @Query("text") text: String,
        @Query("extras") extras: String = "url_s",
        @Query("format") format: String = "json",
        @Query("nojsoncallback") nojsoncallback: Int = 1
    ) : Call<FlickrResponse>

}