package com.vmh.kubetool.screen.main.posts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vmh.kubetool.R
import com.vmh.kubetool.data.models.Post
import com.vmh.kubetool.utils.OnItemRecyclerViewClickListener
import com.vmh.kubetool.utils.PostItemDiffCallback
import kotlinx.android.synthetic.main.layout_posts_item.view.*

class PostsAdapter : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {
    private var posts = mutableListOf<Post>()
    private var listener: OnItemRecyclerViewClickListener<Post>? = null

    @NonNull
    override fun onCreateViewHolder(@NonNull viewGroup: ViewGroup, i: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.layout_posts_item, viewGroup, false)
        return ViewHolder(view, listener!!)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.bindViewData(posts[i])
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    fun submitList(posts: List<Post>) {
        val oldList = this.posts
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
            PostItemDiffCallback(
                oldList,
                posts
            )
        )
        this.posts = posts.toMutableList()
        diffResult.dispatchUpdatesTo(this)
    }

    fun setOnItemRecyclerViewClickListener(listener: OnItemRecyclerViewClickListener<Post>?) {
        this.listener = listener
    }

    inner class ViewHolder(
        @NonNull itemView: View,
        private val listener: OnItemRecyclerViewClickListener<Post>
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        override fun onClick(v: View) {
            listener.onItemClick(posts[adapterPosition])
        }

        fun bindViewData(data: Post) {
            itemView.textViewTimeUpcoming.text = data.title
        }
    }

}
