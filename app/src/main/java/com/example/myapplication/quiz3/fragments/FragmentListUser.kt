package com.example.myapplication.quiz3.fragments

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentUsersBinding
import com.example.myapplication.quiz3.model.UserList
import com.example.myapplication.quiz3.network.NetworkManager3.service
import retrofit2.Call
import retrofit2.Response

class FragmentListUser : Fragment(R.layout.fragment_users) {
    lateinit var binding: FragmentUsersBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUsersBinding.bind(view)
        init()
    }

    private fun init() {
        val context = requireContext()
        with(binding) {
            quiz3Search.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    (quiz3Users.adapter as Quiz3Adapter).filter.filter(newText)
                    return false
                }

            })
            binding.quiz3Users.apply {
                layoutManager = GridLayoutManager(context, 1)
            }
            val call = service.getUsers()
            call.enqueue(object : retrofit2.Callback<UserList> {
                override fun onResponse(call: Call<UserList>, response: Response<UserList>) {
                    binding.quiz3Users.adapter = Quiz3Adapter(this@FragmentListUser, response.body()!!)
                }

                override fun onFailure(call: Call<UserList>, t: Throwable) {
                    Toast.makeText(context, "Load Failed", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

}