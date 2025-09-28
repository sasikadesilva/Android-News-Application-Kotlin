package com.example.newsapp.domain.repository

import androidx.lifecycle.LiveData
import com.example.newsapp.data.model.entity.Article
import com.example.newsapp.data.model.response.NewsResponse

interface ArticleRepository {
    suspend fun getLatestNews(country: String): NewsResponse
    suspend fun getAllNewsWithCategory( category: String): NewsResponse
    suspend fun onSearchNews(category: String, from: String, to: String): NewsResponse

    suspend fun addFavouriteArticle(article: Article)

    suspend fun getFavouriteArticles(userId : Int): LiveData<List<Article>>

    suspend fun getAllFavouriteArticlesByTitleAndAuthor(userId : Int,title : String ,author : String): LiveData<Article>

    suspend fun deleteFavouriteArticle(article: Article)

}