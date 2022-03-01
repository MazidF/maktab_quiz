package com.example.myapplication.quiz3.network

import android.util.Log
import com.example.myapplication.quiz2.service.MyService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager3 {
    val client = OkHttpClient.Builder()
        .addInterceptor(getInterceptor())
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    private fun getInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val newRequest = request.newBuilder()
                .addHeader("Authorization", "Bearer mazidabadi_farahani")
                .build()
            chain.proceed(newRequest).also {
                Log.d("interceptor_client", "status code: " + it.code().toString())
            }
        }
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://papp.ir/api/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val service = retrofit.create(Quiz3Service::class.java)
}