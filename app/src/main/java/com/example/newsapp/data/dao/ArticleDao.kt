package com.example.newsapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.data.model.entity.Article
import com.example.newsapp.data.model.entity.UserModel

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteArticle(article: Article)

    @Delete
    suspend fun deleteFavouriteArticle(article: Article)

    @Query("SELECT * FROM article WHERE user_id = :userId ")
    fun getAllFavouriteArticles(userId : Int): LiveData<List<Article>>

    @Query("SELECT * FROM article WHERE user_id = :userId AND title = :title AND author = :author Limit 1 ")
    fun getAllFavouriteArticlesByTitleAndAuthor(userId : Int,title : String ,author : String): LiveData<Article>
}