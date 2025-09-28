package com.example.newsapp.presentation.ui.activity.dashboard.ui.main.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.data.model.entity.Article
import com.example.newsapp.presentation.ui.activity.dashboard.NewsViewActivity
import com.google.gson.Gson

class LatestArticleAdapter (
    private var articles: List<Article>
) : RecyclerView.Adapter<LatestArticleAdapter.ArticleViewHolder>() {


    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imageView)
        val title: TextView = itemView.findViewById(R.id.titleText)

        val content: TextView = itemView.findViewById(R.id.contentText)

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(articles: List<Article>){
        this.articles = articles
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.latest_article_item, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.title.text = article.title
        val safeContent = article.content ?: ""
        holder.content.text =  Html.fromHtml(safeContent, Html.FROM_HTML_MODE_LEGACY)!!
        Glide.with(holder.itemView.context)
            .load(article.urlToImage)
            .placeholder(R.drawable.image_place_holder)
            .error(R.drawable.image_place_holder)
            .into(holder.image)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, NewsViewActivity::class.java)
            intent.putExtra("article", Gson().toJson(article))
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = articles.size
}