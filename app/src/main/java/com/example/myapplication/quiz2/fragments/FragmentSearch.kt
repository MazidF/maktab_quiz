package com.example.myapplication.quiz2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSearchBinding
import com.example.myapplication.quiz2.model.ViewModelQuiz2
import com.example.myapplication.quiz2.network.NetworkManager.service
import com.example.myapplication.quiz2.photo.FlickrResponse
import com.example.myapplication.quiz2.photo.ItemPhoto
import retrofit2.Call
import retrofit2.Response

class FragmentSearch : Fragment(R.layout.fragment_search) {
    val model: ViewModelQuiz2 by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = DataBindingUtil.bind<FragmentSearchBinding>(view)!!
        val context = requireContext()
        binding.listSearch.apply {
            model.searchList?.let {
                val adapter = Quiz2Adapter(context, it)
                this.adapter = adapter
            }
            layoutManager = LinearLayoutManager(context)
        }
        binding.btnSearch.setOnClickListener {
            service.search(text = binding.text.text.toString()).enqueue(object : retrofit2.Callback<FlickrResponse> {
                override fun onResponse(
                    call: Call<FlickrResponse>,
                    response: Response<FlickrResponse>
                ) {
                    binding.listSearch.apply {
                        val adapter = Quiz2Adapter(context, convertor(response.body() ?: return@apply))
                        this.adapter = adapter
                    }
                }

                override fun onFailure(call: Call<FlickrResponse>, t: Throwable) {
                    Toast.makeText(context, "Failed!!", Toast.LENGTH_SHORT).show()
                }

            })
        }
    }


    private fun convertor(response: FlickrResponse): List<ItemPhoto> {
        return response.photos.photo.map {
            ItemPhoto(it.title, it.url_s)
        }.also {
            model.searchList = it
        }
    }
}