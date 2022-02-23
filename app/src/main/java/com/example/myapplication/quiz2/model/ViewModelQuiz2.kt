package com.example.myapplication.quiz2.model

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.quiz2.network.NetworkManager
import com.example.myapplication.quiz2.photo.FlickrResponse
import com.example.myapplication.quiz2.photo.ItemPhoto
import retrofit2.Call
import retrofit2.Response

class ViewModelQuiz2 : ViewModel() {
    var searchList: List<ItemPhoto>? = null
    val flickrResponse = MutableLiveData<FlickrResponse>()

    fun get(context: Context) {
        val call = NetworkManager.service.get(per_page = 40)
        call.enqueue(object : retrofit2.Callback<FlickrResponse> {
            override fun onResponse(
                call: Call<FlickrResponse>,
                response: Response<FlickrResponse>
            ) {
                flickrResponse.postValue(response.body())
            }

            override fun onFailure(call: Call<FlickrResponse>, t: Throwable) {
                Toast.makeText(context, "Failed!!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}