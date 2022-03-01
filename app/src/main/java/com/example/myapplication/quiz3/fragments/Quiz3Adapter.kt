package com.example.myapplication.quiz3.fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.UserItemBinding
import com.example.myapplication.quiz3.model.UserList
import com.example.myapplication.quiz3.model.UserListItem

class Quiz3Adapter(fragment: Fragment, val userList: UserList) : RecyclerView.Adapter<Quiz3Adapter.MyHolder>() {
    val inflater = LayoutInflater.from(fragment.requireContext())
    val controller = fragment.findNavController()

    inner class MyHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = UserItemBinding.bind(view).apply {
            rootList.setOnClickListener {
                controller.navigate(FragmentListUserDirections.actionFragmentListUserToFragmentUser(this.user!!.toItem()))
            }
        }

        fun bind(item: UserListItem) {
            binding.user = item
        }
    }

    override fun getItemCount() = userList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = inflater.inflate(R.layout.user_item, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(userList[position])
    }

}
