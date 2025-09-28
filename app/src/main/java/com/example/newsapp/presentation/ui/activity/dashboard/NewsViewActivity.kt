package com.example.newsapp.presentation.ui.activity.dashboard

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.data.model.entity.Article
import com.example.newsapp.presentation.viewmodel.ArticleViewModel
import com.example.newsapp.presentation.viewmodel.UserViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class NewsViewActivity : AppCompatActivity() {

    private val articleViewModel: ArticleViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var headerImage: ImageView
    private lateinit var title: TextView
    private lateinit var content: TextView
    private lateinit var author: TextView
    private lateinit var date: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_view)

        val article = Gson().fromJson(intent.getStringExtra("article"), Article::class.java)
        if (article == null) {
            finish()
            return

        }

        headerImage = findViewById(R.id.headerImage)
        title = findViewById(R.id.newsTitle)
        content = findViewById(R.id.articleContent)
        author = findViewById(R.id.newsAuthor)
        date = findViewById(R.id.newsDate)

        val backButton = findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }

        val addArticle = findViewById<FloatingActionButton>(R.id.addArticle)
        addArticle.setOnClickListener {
            lifecycleScope.launch {
                val userModel = userViewModel.getUserSessionInfo()

                if (userModel != null) {
                    article.userId = userModel.id
                    articleViewModel.addFavouriteArticle(article)
                }
            }
        }

        lifecycleScope.launch {
            val userModel = userViewModel.getUserSessionInfo()
            if (userModel != null) {
               articleViewModel.getAllFavouriteArticlesByTitleAndAuthor(
                    userModel.id,
                    article.title,
                    article.author
                ).observe(this@NewsViewActivity) {
                    if (it != null) {
                        addArticle.visibility = View.GONE
                    }
               }

            }

        }

        title.text = article.title
        val safeContent = article.content ?: ""
        content.text = Html.fromHtml(safeContent, Html.FROM_HTML_MODE_LEGACY)!!
        author.text = article.author
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            date.text = formatUtcDate(article.publishedAt)
        } else {
            date.text = article.publishedAt
        }

        Glide.with(this)
            .load(article.urlToImage)
            .placeholder(R.drawable.image_place_holder)
            .error(R.drawable.image_place_holder)
            .into(headerImage)

    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun formatUtcDate(dateString: String): String {
        try {
            val dateTime = OffsetDateTime.parse(dateString)
            val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
            return dateTime.format(formatter)
        } catch (e: Exception) {
            return dateString
        }

    }
}