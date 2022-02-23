package com.example.myapplication.quiz2.network

import com.example.myapplication.quiz2.service.MyService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {
    val client = OkHttpClient.Builder()
        .addInterceptor(getInterceptor())
        .build()

    private fun getInterceptor(headers: HashMap<String, String>? = null): Interceptor {
        if (headers != null) {
            return object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val builder = chain.request().newBuilder()
                    for((key, value) in headers) {
                        builder.addHeader(key, value)
                    }
                    val request = builder.build()
                    return chain.proceed(request)
                }
            }
        }
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.flickr.com")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val service = retrofit.create(MyService::class.java)
}