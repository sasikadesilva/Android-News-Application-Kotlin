package com.example.newsapp.presentation.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.newsapp.R
import com.example.newsapp.data.model.UserModel
import com.example.newsapp.presentation.ui.activity.auth.LoginActivity
import com.example.newsapp.presentation.ui.activity.dashboard.HomeActivity
import com.example.newsapp.presentation.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.getValue

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        lifecycleScope.launch {
            val userModel = userViewModel.getUserSessionInfo()

           if(userModel == null){
               val intent = Intent(this@SplashScreenActivity, LoginActivity::class.java)
               intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
               startActivity(intent)
           }
            else{
               val intent = Intent(this@SplashScreenActivity, HomeActivity::class.java)
               intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
               startActivity(intent)
           }


            finish()
        }


    }

    override fun onDestroy() {
        super.onDestroy()
    }
}