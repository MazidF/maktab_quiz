package com.example.myapplication.quiz3.model

import java.io.Serializable

data class UserItem(
    val firstName: String,
    val hobbies: List<String>,
    val lastName: String,
    val nationalCode: String
) : Serializable {
    lateinit var id: String
}