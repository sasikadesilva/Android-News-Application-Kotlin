package com.example.newsapp.presentation.ui.activity.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.newsapp.databinding.ActivityHomeBinding
import com.example.newsapp.R
import com.example.newsapp.presentation.ui.activity.dashboard.ui.main.FavouriteItemFragment
import com.example.newsapp.presentation.ui.activity.dashboard.ui.main.HomeFragment
import com.example.newsapp.presentation.ui.activity.dashboard.ui.main.ProfileFragment
import com.example.newsapp.presentation.viewmodel.ArticleViewModel
import com.example.newsapp.presentation.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private  val articleViewModel: ArticleViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set default fragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, HomeFragment())
                .commit()
        }

        binding.bottomNav.setOnItemSelectedListener { item ->
            val selectedFragment = when (item.itemId) {
                R.id.home -> HomeFragment()
                R.id.favourite -> FavouriteItemFragment()
                R.id.profile -> ProfileFragment()
                else -> HomeFragment()
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, selectedFragment)
                .commit()




            true
        }


        val badge = binding.bottomNav.getOrCreateBadge(R.id.favourite)


        lifecycleScope.launch {
            val userModel = userViewModel.getUserSessionInfo()
            if (userModel != null) {
                articleViewModel.getFavouriteArticles(userModel.id).observe(this@HomeActivity) { news ->
                    if (news?.isNotEmpty() == true) {
                        badge.isVisible = true   // show badge
                        badge.number = news.size
                    } else {
                        badge.isVisible = false
                    }
                }
            } else {
                badge.isVisible = false
            }
        }

    }
}
