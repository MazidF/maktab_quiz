package com.example.myapplication.quiz3.model

data class User(val name: String, val family: String, val nationalCode: String) {
    var hobbies: List<String>? = null
    lateinit var id: String
}
