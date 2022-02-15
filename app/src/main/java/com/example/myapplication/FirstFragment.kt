package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.databinding.FragmentFristBinding
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future

class FirstFragment : Fragment(R.layout.fragment_frist) {
    lateinit var client: OkHttpClient
    val executor by lazy {
        Executors.newSingleThreadExecutor()
    }
    lateinit var binding: FragmentFristBinding
    val liveData by lazy {
        MutableLiveData<String>()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_frist, container, false)
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

/*        val future:Future<String> = executor.submit(object : Callable<String> {
            override fun call(): String {
                val response = client.newCall(request).execute()
                return response.body()?.string() ?: "Something is WRONG!!"
            }
        })

        binding.text.text = future.get()*/

        Thread {
            val response = client.newCall(request).execute()
            response.body()?.run {
                val string = string()
                liveData.postValue(string)
            }
        }.start()
    }
}
