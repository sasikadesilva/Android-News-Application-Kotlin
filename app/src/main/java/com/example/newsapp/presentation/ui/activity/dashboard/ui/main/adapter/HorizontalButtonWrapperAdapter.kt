package com.example.newsapp.presentation.ui.activity.dashboard.ui.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R

class HorizontalButtonWrapperAdapter(
    private var buttons: List<String>,
    private val onItemClick: (position: Int) -> Unit
) :
    RecyclerView.Adapter<HorizontalButtonWrapperAdapter.ViewHolder>() {

    val buttons233 = buttons

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recyclerView: RecyclerView = itemView.findViewById(R.id.innerRecyclerView)

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(buttons: List<String>) {
        this.buttons = buttons
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_layout, parent, false)

        val viewHolder = ViewHolder(view)
        val adapter = HorizontalButtonAdapter(buttons) { position ->
            viewHolder.recyclerView.scrollToPosition(position)
            onItemClick.invoke(position)
        }
        viewHolder.recyclerView.layoutManager =
            LinearLayoutManager(viewHolder.itemView.context, LinearLayoutManager.HORIZONTAL, false)
        viewHolder.recyclerView.adapter = adapter
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


    }

    override fun getItemCount() = 1


}