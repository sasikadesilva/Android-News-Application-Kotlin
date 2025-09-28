package com.example.newsapp.presentation.ui.activity.dashboard.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.google.android.material.button.MaterialButton

class HorizontalButtonAdapter(
    private var items: List<String>,
    private val onItemClick: (position: Int) -> Unit
) : RecyclerView.Adapter<HorizontalButtonAdapter.ButtonViewHolder>() {

    private var selectedPosition = 0

    inner class ButtonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val button: MaterialButton = view.findViewById(R.id.buttonItem)
    }

    fun updateData(items: List<String>){
        this.items = items

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rounded_button, parent, false)
        return ButtonViewHolder(view)
    }

    override fun onBindViewHolder(holder: ButtonViewHolder, position: Int) {
        val ctx = holder.itemView.context
        holder.button.text = items[position]

        if (position == selectedPosition) {

            holder.button.setBackgroundColor(ContextCompat.getColor(ctx, R.color.button_enable_color))
            holder.button.setTextColor(ContextCompat.getColor(ctx, R.color.white))
            holder.button.strokeWidth = 0
        } else {
            // Inactive: white background, dark text, gray stroke
            holder.button.setBackgroundColor(ContextCompat.getColor(ctx, R.color.button_disable_color))
            holder.button.setTextColor(ContextCompat.getColor(ctx, R.color.placeholder_color))
//            holder.button.strokeWidth = ctx.resources.getDimensionPixelSize(R.dimen.stroke_width_1dp)
            holder.button.strokeColor = ContextCompat.getColorStateList(ctx, R.color.placeholder_color)
        }

        holder.button.setOnClickListener {
            val previous = selectedPosition
            selectedPosition = position
            notifyItemChanged(previous)
            notifyItemChanged(selectedPosition)
            onItemClick(position)
        }

    }

    override fun getItemCount(): Int = items.size

    fun setSelected(position: Int) {
        val previous = selectedPosition
        selectedPosition = position
        notifyItemChanged(previous)
        notifyItemChanged(selectedPosition)
    }
}
