package com.example.newsapp.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "article",primaryKeys = ["author","title","user_id"])
class Article {
    @ColumnInfo("author")
    var author: String = ""
    @ColumnInfo("title")
    var title: String = ""

    @ColumnInfo("description")
    var description: String = ""

    @ColumnInfo("url")
    var url: String = ""

    @ColumnInfo("urlToImage")
    var urlToImage: String = ""

    @ColumnInfo("publishedAt")
    var publishedAt: String = ""

    @ColumnInfo("content")
    var content: String = ""

    @ColumnInfo("user_id")
    var userId: Int = 0

}