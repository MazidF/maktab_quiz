package com.example.myapplication.quiz1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSecondBinding
import okhttp3.*
import java.io.IOException

class SecondFragment : Fragment(R.layout.fragment_second) {
    lateinit var client: OkHttpClient
    lateinit var binding: FragmentSecondBinding
    val liveData by lazy {
        MutableLiveData<String>()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_second, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.liveData = liveData
        binding.lifecycleOwner = this

        client = OkHttpClient()
        val request = Request.Builder()
            .url("https://picsum.photos/seed/picsum/200/300")
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val text = e.message.toString()
                liveData.postValue(text)
            }

            override fun onResponse(call: Call, response: Response) {
                val text = response.body()?.string() ?: "Something is WRONG!!"
                liveData.postValue(text)
            }

        })
    }
}