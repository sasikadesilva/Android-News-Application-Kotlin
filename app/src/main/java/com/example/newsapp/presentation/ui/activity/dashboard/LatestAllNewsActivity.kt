package com.example.newsapp.presentation.ui.activity.dashboard

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.presentation.ui.activity.dashboard.ui.main.adapter.FavouriteItemRecyclerViewAdapter
import com.example.newsapp.presentation.viewmodel.ArticleViewModel
import com.example.newsapp.presentation.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.getValue

@AndroidEntryPoint
class LatestAllNewsActivity : AppCompatActivity() {
    private val userViewModel: UserViewModel by viewModels()
    private val articleViewModel: ArticleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latest_all_news)

        val recyclerView = findViewById<RecyclerView>(R.id.list)
        // Set the adapter
        val adapter = FavouriteItemRecyclerViewAdapter(emptyList(),false) { article ->
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val backButton = findViewById<View>(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }

        articleViewModel.loadLatestArticles()
        lifecycleScope.launch {
                articleViewModel.latestNews.collect { news ->
                    adapter.updateData(news)
                }
        }
    }
}