package com.example.myapplication.quiz2.photo

import com.example.myapplication.quiz2.photo.Photo

data class Photos(
    val page: Int,
    val pages: Int,
    val perpage: Int,
    val photo: List<Photo>,
    val total: Int
)