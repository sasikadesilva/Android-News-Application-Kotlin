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
import com.example.newsapp.databinding.FavouriteFragmentItemBinding
import com.example.newsapp.presentation.ui.activity.dashboard.NewsViewActivity
import com.example.newsapp.presentation.ui.activity.dashboard.ui.main.placeholder.PlaceholderContent
import com.google.gson.Gson

/**
 * [androidx.recyclerview.widget.RecyclerView.Adapter] that can display a [com.example.newsapp.presentation.ui.activity.dashboard.ui.main.placeholder.PlaceholderContent.PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class FavouriteItemRecyclerViewAdapter(
    private var values: List<Article> ,
    private var isDeleteRequired : Boolean,
    private val onItemClick: (article: Article) -> Unit
) : RecyclerView.Adapter<FavouriteItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FavouriteFragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: List<Article>) {
        this.values = newData
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.title.text = item.title
        val safeContent = item.content ?: ""
        holder.contentView.text =  Html.fromHtml(safeContent, Html.FROM_HTML_MODE_LEGACY)!!
        Glide.with(holder.itemView.context)
            .load(item.urlToImage)
            .placeholder(R.drawable.image_place_holder)
            .error(R.drawable.image_place_holder)
            .into(holder.headerImage)

        holder.readMore.setOnClickListener {
            val intent = Intent(holder.itemView.context, NewsViewActivity::class.java)
            intent.putExtra("article", Gson().toJson(item))
            holder.itemView.context.startActivity(intent)
        }

        if(isDeleteRequired){
            holder.deleteButton.visibility = View.VISIBLE
            holder.deleteButton.setOnClickListener {
                onItemClick(item)
            }
        }

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FavouriteFragmentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val  title : TextView = binding.articleTitle
        val contentView: TextView = binding.articleDescription
        val headerImage: ImageView = binding.articleImage
        val readMore: TextView = binding.readMore

        val deleteButton = binding.deleteButton

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}