package com.example.newsapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.model.entity.Article
import com.example.newsapp.domain.usecase.ArticleUseCase
import com.example.newsapp.domain.usecase.UserPreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val userPreferencesUseCase: UserPreferencesUseCase,
    private val articleUseCase: ArticleUseCase
) : ViewModel() {

     val _latestNews = MutableStateFlow<List<Article>>(emptyList())
    val latestNews: StateFlow<List<Article>> = _latestNews

    private val _newsFeed = MutableStateFlow<List<Article>>(emptyList())
    val newsFeed: StateFlow<List<Article>> = _newsFeed


    //  TODO("add pagination")

    fun loadLatestArticles() {
        viewModelScope.launch {
            val response = articleUseCase.getLatestNews("us")
            if (response.status == "ok") {
                _latestNews.value = response.articles
            }

        }
    }

    fun getAllNewsWithCategory(category: String) {
        viewModelScope.launch {
            val response = articleUseCase.getAllNewsWithCategory(category)
            if (response.status == "ok") {
                _newsFeed.value = response.articles
            }

        }

    }

    fun onSearchNews(searchString: String) {
        viewModelScope.launch {
            val response = articleUseCase.onSearchNews(searchString, "2025-09-01", "publishedAt")
            if (response.status == "ok") {
                _newsFeed.value = response.articles
            }
        }
    }

    fun addFavouriteArticle(article: Article) {
        viewModelScope.launch {
            articleUseCase.addFavouriteArticle(article)
        }
    }

    suspend fun getFavouriteArticles(userId: Int): LiveData<List<Article>> {
        return articleUseCase.getFavouriteArticles(userId)
    }


    suspend fun getAllFavouriteArticlesByTitleAndAuthor(
        userId: Int,
        title: String,
        author: String
    ): LiveData<Article> {
        return articleUseCase.getAllFavouriteArticlesByTitleAndAuthor(userId, title, author)
    }

   fun deleteFavouriteArticle(article: Article) {
       viewModelScope.launch {
           articleUseCase.deleteFavouriteArticle(article)
       }

    }
}