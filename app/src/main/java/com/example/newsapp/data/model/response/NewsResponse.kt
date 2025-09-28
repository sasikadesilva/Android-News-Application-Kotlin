package com.example.newsapp.data.model.response

import com.example.newsapp.data.model.entity.Article

class NewsResponse {
    var status: String =""
    var totalResults: Int =0
    var articles: List<Article> = emptyList()
}