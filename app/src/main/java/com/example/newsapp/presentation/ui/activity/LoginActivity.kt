package com.example.newsapp.presentation.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.R
import com.example.newsapp.data.model.UserModel
import com.example.newsapp.presentation.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint


class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val signUpButton = findViewById<TextView>(R.id.signUpButton)
        signUpButton.setOnClickListener {
            registerUser()
        }




    }

private fun registerUser() {
    val intent = Intent(this, RegistrationActivity::class.java)
    startActivity(intent)
}
}