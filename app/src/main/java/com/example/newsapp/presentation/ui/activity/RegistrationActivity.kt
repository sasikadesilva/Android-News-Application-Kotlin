package com.example.newsapp.presentation.ui.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.newsapp.R
import com.example.newsapp.data.model.UserModel
import com.example.newsapp.presentation.viewmodel.UserViewModel
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class RegistrationActivity : AppCompatActivity() {
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registration)

        val signUpButton = findViewById<Button>(R.id.signUpButton)
        signUpButton.setOnClickListener {
            onRegisterUser()
        }

    }

    private fun onRegisterUser(){
        val userToRegister = UserModel()
        // This line has a syntax error
        userToRegister.firstName = ((findViewById<TextInputEditText>(R.id.firstNameText)).text.toString() as String?)!!
        userToRegister.lastName = ((findViewById<TextInputEditText>(R.id.lastNameText)).text.toString() as String?)!!
        userToRegister.email = ((findViewById<TextInputEditText>(R.id.emailText)).text.toString() as String?)!!
        userToRegister.password=((findViewById<TextInputEditText>(R.id.passwordText)).text.toString() as String?)!!
        userToRegister.password=((findViewById<TextInputEditText>(R.id.confirmPasswordText)).text.toString() as String?)!!

        // 3. Call the registerUser function on the ViewModel instance
        try {
            userViewModel.registerUser(userToRegister)
        }catch (Exception: Exception){

            System.out.println("======erroe========="+Exception.message)

        }

    }
}