package com.example.newsapp.presentation.ui.activity.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.newsapp.R
import com.example.newsapp.data.model.UserModel
import com.example.newsapp.presentation.viewmodel.UserViewModel
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancel

@AndroidEntryPoint
class RegistrationActivity : AppCompatActivity() {
    private val userViewModel: UserViewModel by viewModels()

    private lateinit var firstNameEditText: TextInputEditText
    private lateinit var lastNameEditText: TextInputEditText
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var confirPasswordEditText: TextInputEditText
    private var livedataEmail : LiveData<UserModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        firstNameEditText = findViewById(R.id.firstNameText)
        lastNameEditText = findViewById(R.id.lastNameText)
        emailEditText = findViewById(R.id.emailText)
        passwordEditText = findViewById(R.id.passwordText)
        confirPasswordEditText = findViewById(R.id.confirmPasswordText)
        val signUpButton = findViewById<Button>(R.id.signUpButton)
        val backButton = findViewById<ImageView>(R.id.backButton)

        // Add TextWatcher to monitor changes in all TextViews
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                enableSubmitButton()
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        firstNameEditText.addTextChangedListener(textWatcher)
        lastNameEditText.addTextChangedListener(textWatcher)
        emailEditText.addTextChangedListener(textWatcher)
        passwordEditText.addTextChangedListener(textWatcher)
        confirPasswordEditText.addTextChangedListener(textWatcher)


        //button click events
        signUpButton.setOnClickListener {
            onRegisterUser()
        }

        backButton.setOnClickListener {
            this.onBackPressed()
        }


        // Observe for registration success status
        userViewModel.registrationSuccess.observe(this) { isSuccess ->
            if (isSuccess) {
                Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show()
                // Navigate to the next screen, e.g., LoginActivity
                // startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        // Observe for any errors
        userViewModel.error.observe(this) { errorMessage ->
            // Show the error from the UseCase in a Toast
            Toast.makeText(this, "Error: $errorMessage", Toast.LENGTH_LONG).show()
        }

    }

    private fun onRegisterUser() {
        val userToRegister = UserModel()
        userToRegister.firstName = firstNameEditText.text.toString()
        userToRegister.lastName = lastNameEditText.text.toString()
        userToRegister.email = emailEditText.text.toString()
        userToRegister.password = passwordEditText.text.toString()
        userToRegister.password = confirPasswordEditText.text.toString()

        livedataEmail = userViewModel.getUserByEmail(userToRegister.email)
        livedataEmail?.observe(this, Observer { user ->
            if (user != null) {
                // Login successful, navigate to the main activity
                Toast.makeText(this, "Email already exists", Toast.LENGTH_SHORT)
                    .show()
            } else {

                userViewModel.registerUser(userToRegister)
                firstNameEditText.text?.clear()
                lastNameEditText.text?.clear()
                emailEditText.text?.clear()
                passwordEditText.text?.clear()
                confirPasswordEditText.text?.clear()
                livedataEmail!!.removeObservers(this)
            }
        })



    }

    private fun enableSubmitButton() {
        val signUpButton = findViewById<Button>(R.id.signUpButton)
        var isAllFilled = firstNameEditText.text?.isNotEmpty()!! &&
                lastNameEditText.text?.isNotEmpty()!! &&
                emailEditText.text?.isNotEmpty()!! &&
                passwordEditText.text?.isNotEmpty()!! &&
                confirPasswordEditText.text?.isNotEmpty()!!
        passwordEditText.error = null
        confirPasswordEditText.error = null
        if(isAllFilled){
            if (passwordEditText.text.toString() != confirPasswordEditText.text.toString() && confirPasswordEditText.text?.isNotEmpty()!! && confirPasswordEditText.text?.length!! > 8) {
                confirPasswordEditText.error = "Passwords do not match"
                isAllFilled = false
            }
            val email = emailEditText.text.toString()
            if (!email.contains("@") || !email.contains(".")) {

                emailEditText.error = "Invalid email address"
                isAllFilled = false
            }
        }


        signUpButton.isEnabled = isAllFilled
    }

    override fun onDestroy() {
        super.onDestroy()
        livedataEmail?.removeObservers(this)
    }
}