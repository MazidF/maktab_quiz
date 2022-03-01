package com.example.myapplication.quiz3.model

data class UserListItem(
    val _id: String,
    val firstName: String,
    val lastName: String,
    val nationalCode: String
) {
    fun toItem() = UserItem(firstName, listOf(), lastName, nationalCode).apply {
        this.id = _id
    }
}