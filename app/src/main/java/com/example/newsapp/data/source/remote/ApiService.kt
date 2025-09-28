package com.example.newsapp.data.source.remote

import com.example.newsapp.data.model.response.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top-headlines")
    suspend fun getLatestNews(@Query("country") country: String,
                              @Query("apiKey") apiKey: String): NewsResponse

    @GET("everything")
    suspend fun getAllNewsWithCategory(@Query("q") category: String,
                                       @Query("apiKey") apiKey: String): NewsResponse

    @GET("everything")
    suspend fun onSearchNews(@Query("q") category: String,
                             @Query("from") from: String,
                             @Query("sortBy") to: String,
                             @Query("apiKey") apiKey: String): NewsResponse
}