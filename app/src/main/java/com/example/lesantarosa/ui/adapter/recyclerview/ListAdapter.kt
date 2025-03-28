package com.example.lesantarosa.ui.adapter.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class ListAdapter<T>: RecyclerView.Adapter<ListAdapter.ListViewHolder<T>>() {

    private val values = mutableListOf<T>()

    abstract class ListViewHolder<T>(itemView: View): RecyclerView.ViewHolder(itemView) {

        abstract fun setViewInfo(value: T)
    }

    override fun onBindViewHolder(holder: ListViewHolder<T>, position: Int) {
        val value = values[position]
        holder.setViewInfo(value)
    }

    override fun getItemCount(): Int = values.size

    fun refresh(values: List<T>) {
        this.values.clear()
        this.values.addAll(values)
        notifyDataSetChanged()
    }
}