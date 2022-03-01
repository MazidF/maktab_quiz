package com.example.myapplication.quiz3.network

import com.example.myapplication.quiz3.model.User
import com.example.myapplication.quiz3.model.UserItem
import com.example.myapplication.quiz3.model.UserList
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface Quiz3Service {

    @POST("users")
    fun setUser(
        @Body user: UserItem
    ) : Call<ResponseBody>


    @POST("users")
    fun setUser(
        @Query("firstName") name: String,
        @Query("lastName") family: String,
        @Query("nationalCode") nationalCode: String,
        @Query("hobbies") hobbies: List<String>
    ) : Call<ResponseBody>

    @Multipart
    @POST("users/{userId}/image")
    fun setImage(
        @Path("userId") userId: String,
        @Part image: MultipartBody.Part
    ) : Call<ResponseBody>

    @GET("users")
    fun getUsers() : Call<UserList>
}
