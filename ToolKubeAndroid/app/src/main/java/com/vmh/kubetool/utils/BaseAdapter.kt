package com.vmh.kubetool.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseViewHolder<T>>() {
    private lateinit var listener: OnItemRecyclerViewClickListener<T>
    private var data = mutableListOf<T>()

    fun setOnItemClickListener(listener: OnItemRecyclerViewClickListener<T>) {
        this.listener = listener
    }

    fun replaceItem(item: List<T>) {
        data.clear()
        data.addAll(item)
        notifyDataSetChanged()
    }

    fun addItem(item: T) {
        data.add(item)
        notifyItemChanged(data.indexOf(item))
    }

    fun addItems(item: List<T>) {
        val originSize = data.size
        data.addAll(item)
        notifyItemRangeInserted(originSize, item.size)
    }

    fun removeItem(item: T) {
        if (item in data) {
            val positionItem = data.indexOf(item)
            data.removeAt(positionItem)
            notifyItemRemoved(positionItem)
        }
    }

    fun clearList() {
        data.clear()
        notifyDataSetChanged()
    }

    protected abstract fun layout(row: Int): Int

    protected abstract fun viewHolder(view: View): BaseViewHolder<T>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        return viewHolder(LayoutInflater.from(parent.context).inflate(viewType, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bindData(data[position], listener)
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int = layout(position)
}
