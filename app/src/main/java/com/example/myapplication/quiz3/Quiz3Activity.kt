package com.example.myapplication.quiz3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityQuiz3Binding

class Quiz3Activity : AppCompatActivity() {
    val controller by lazy {
        findNavController(R.id.quiz3_container)
    }
    lateinit var binding: ActivityQuiz3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuiz3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        NavigationUI.setupWithNavController(binding.quiz3Bottom, controller)
    }
}
