package com.example.myapplication.quiz3.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.UserItemBinding
import com.example.myapplication.quiz3.model.UserList
import com.example.myapplication.quiz3.model.UserListItem

class Quiz3Adapter(fragment: Fragment, val userList: UserList) : RecyclerView.Adapter<Quiz3Adapter.MyHolder>(), Filterable {
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

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val list = userList.filter {
                    it.firstName == p0 || it.lastName == p0 || it.nationalCode == p0 || it._id == p0
                }
                val filterResult = FilterResults()
                filterResult.values = list
                return filterResult
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                val filteredList = p1?.values as List<UserListItem>
                userList.clear()
                userList.addAll(filteredList)
                notifyDataSetChanged()
            }

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
