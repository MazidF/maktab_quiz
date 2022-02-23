package com.example.myapplication.quiz2.fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.PhotoItemBinding
import com.example.myapplication.quiz2.photo.ItemPhoto
import com.squareup.picasso.Picasso

class Quiz2Adapter(val context: Context, val list: List<ItemPhoto>) : RecyclerView.Adapter<Quiz2Adapter.MyHolder>() {
    val inflater = LayoutInflater.from(context)

    inner class MyHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: PhotoItemBinding = DataBindingUtil.bind(view)!!

        fun bind(photo: ItemPhoto) {
            binding.photo = photo
            Picasso
                .with(context)
                .load(photo.url)
                .into(binding.photoImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = inflater.inflate(R.layout.photo_item, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

}
