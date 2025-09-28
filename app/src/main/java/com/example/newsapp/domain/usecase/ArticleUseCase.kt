package com.example.newsapp.domain.usecase

import androidx.lifecycle.LiveData
import com.example.newsapp.data.model.entity.Article
import com.example.newsapp.data.model.response.NewsResponse
import com.example.newsapp.data.repository.ArticleRepositoryImpl
import com.example.newsapp.domain.repository.ArticleRepository
import javax.inject.Inject

class ArticleUseCase @Inject constructor(private val articleRepository: ArticleRepository) {

    suspend fun getLatestNews(country: String): NewsResponse{
        return articleRepository.getLatestNews(country)
    }
    suspend fun getAllNewsWithCategory( category: String): NewsResponse{
        return articleRepository.getAllNewsWithCategory(category)
    }
    suspend fun onSearchNews(category: String, from: String, to: String): NewsResponse{
        return articleRepository.onSearchNews(category, from, to)
    }

    suspend fun addFavouriteArticle(article: Article) {
        articleRepository.addFavouriteArticle(article)
    }

    suspend fun getFavouriteArticles(userId: Int): LiveData<List<Article>> {
        return articleRepository.getFavouriteArticles(userId)
    }

    suspend fun getAllFavouriteArticlesByTitleAndAuthor(
        userId: Int,
        title: String,
        author: String
    ): LiveData<Article> {
        return articleRepository.getAllFavouriteArticlesByTitleAndAuthor(userId, title, author)

    }

    suspend fun deleteFavouriteArticle(article: Article) {
        articleRepository.deleteFavouriteArticle(article)
    }
}