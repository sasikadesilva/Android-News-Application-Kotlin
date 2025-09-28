package com.example.newsapp.presentation.ui.activity.dashboard.ui.main.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.data.model.entity.Article
import com.example.newsapp.presentation.ui.activity.dashboard.LatestAllNewsActivity
import com.example.newsapp.presentation.ui.activity.dashboard.NewsViewActivity
import com.example.newsapp.presentation.ui.activity.dashboard.ui.main.adapter.HorizontalButtonAdapter.ButtonViewHolder
import com.example.newsapp.presentation.ui.activity.dashboard.ui.main.adapter.HorizontalButtonWrapperAdapter.ViewHolder
import com.google.gson.Gson

class LatestArticleWrapperAdapter (private var articles: List<Article>) :
    RecyclerView.Adapter<LatestArticleWrapperAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recyclerView: RecyclerView = itemView.findViewById(R.id.innerRecyclerView)
        val adapter = LatestArticleAdapter(emptyList())
        val nextButton: View = itemView.findViewById(R.id.nextButton)
        val nextButtonText: View = itemView.findViewById(R.id.seeAllText)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(articles: List<Article>){
        this.articles = articles
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.latest_recyclerview_layout, parent, false)
        val viewHolder = ViewHolder(view)
        viewHolder.recyclerView.layoutManager =
            LinearLayoutManager(viewHolder.itemView.context, LinearLayoutManager.HORIZONTAL, false)
        viewHolder.recyclerView.adapter = viewHolder.adapter
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.adapter.updateData(articles)
        holder.nextButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, LatestAllNewsActivity::class.java)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = 1


}