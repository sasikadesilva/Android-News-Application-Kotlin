package com.example.newsapp.data.repository

import androidx.lifecycle.LiveData
import com.example.newsapp.data.dao.ArticleDao
import com.example.newsapp.data.model.entity.Article
import com.example.newsapp.data.model.response.NewsResponse
import com.example.newsapp.data.source.remote.RetrofitClient
import com.example.newsapp.domain.repository.ArticleRepository
import com.example.newsapp.util.API_TOKEN
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val retrofitClient: RetrofitClient,
    private val articleDao: ArticleDao
) :
    ArticleRepository {


    override suspend fun getLatestNews(
        country: String
    ): NewsResponse {
        return retrofitClient.api.getLatestNews(country, API_TOKEN)
    }

    override suspend fun getAllNewsWithCategory(
        category: String
    ): NewsResponse {
        return retrofitClient.api.getAllNewsWithCategory(category, API_TOKEN)
    }

    override suspend fun onSearchNews(
        category: String,
        from: String,
        to: String
    ): NewsResponse {
        return retrofitClient.api.onSearchNews(category, from, to, API_TOKEN)
    }

    override suspend fun addFavouriteArticle(article: Article) {
        articleDao.insertFavouriteArticle(article)
    }

    override suspend fun getFavouriteArticles(userId: Int): LiveData<List<Article>> {
        return articleDao.getAllFavouriteArticles(userId)
    }

    override suspend fun getAllFavouriteArticlesByTitleAndAuthor(
        userId: Int,
        title: String,
        author: String
    ): LiveData<Article> {
        return articleDao.getAllFavouriteArticlesByTitleAndAuthor(userId, title, author)

    }

    override suspend fun deleteFavouriteArticle(article: Article) {
       articleDao.deleteFavouriteArticle(article)
    }
}