package com.example.newsapp.presentation.ui.activity.auth

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.newsapp.R
import com.example.newsapp.data.model.entity.UserModel
import com.example.newsapp.presentation.ui.activity.dashboard.HomeActivity
import com.example.newsapp.presentation.viewmodel.UserViewModel
import com.example.newsapp.util.CryptoUtil
import com.example.newsapp.util.PasswordHasher
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val userViewModel: UserViewModel by viewModels()
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText

    private var livedataEmail : LiveData<UserModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize views
        emailEditText = findViewById(R.id.emailText)
        passwordEditText = findViewById(R.id.passwordText)
        val signUpButton = findViewById<TextView>(R.id.signUpButton)
        val loginButton = findViewById<MaterialButton>(R.id.loginButton)

        // Add TextWatcher to monitor changes in all TextViews
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                enableSubmitButton()
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        emailEditText.addTextChangedListener(textWatcher)
        passwordEditText.addTextChangedListener(textWatcher)

        // Set click listeners
        signUpButton.setOnClickListener {
            registerUser()
        }

        loginButton.setOnClickListener {
                loginUser()
        }


    }


    private  fun loginUser() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            // Call the ViewModel to perform login
            livedataEmail = userViewModel.getUserByEmail(email)
            livedataEmail?.observe(this, Observer { user ->
                if (user != null && PasswordHasher.verifyPassword(password, user.password, user.passwordSalt)) {
                    userViewModel.addUserSessionInfo(user)
                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } else {


                    Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        }
    }

    private fun registerUser() {
        val intent = Intent(this, RegistrationActivity::class.java)
        startActivity(intent)
    }

    private fun enableSubmitButton() {
        val loginButton = findViewById<Button>(R.id.loginButton)
        var isAllFilled =
                emailEditText.text?.isNotEmpty()!! &&
                passwordEditText.text?.isNotEmpty()!!
        passwordEditText.error = null
        emailEditText.error = null
        if(isAllFilled){

            val email = emailEditText.text.toString()
            if (!email.contains("@") || !email.contains(".")) {

                emailEditText.error = "Invalid email address"
                isAllFilled = false
            }
        }


        loginButton.isEnabled = isAllFilled
    }

    override fun onStop() {
        super.onStop()
        emailEditText.text?.clear()
        passwordEditText.text?.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        livedataEmail?.removeObservers(this)
    }
}
