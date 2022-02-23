package com.example.myapplication.quiz2.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.quiz2.model.ViewModelQuiz2
import com.example.myapplication.quiz2.photo.FlickrResponse
import com.example.myapplication.quiz2.photo.ItemPhoto

class FragmentHome : Fragment() {
    val model: ViewModelQuiz2 by activityViewModels()
    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = requireContext()
        binding.listHome.apply {
            model.flickrResponse.observe(viewLifecycleOwner) {
                if (it != null) {
                    val adapter = Quiz2Adapter(context, convertor(it))
                    this.adapter = adapter
                }
            }
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun convertor(response: FlickrResponse): List<ItemPhoto> {
        return response.photos.photo.map {
            ItemPhoto(it.title, it.url_s)
        }
    }
}