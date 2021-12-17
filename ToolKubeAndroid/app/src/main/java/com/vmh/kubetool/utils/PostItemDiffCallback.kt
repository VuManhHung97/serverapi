package com.vmh.kubetool.utils

import androidx.recyclerview.widget.DiffUtil
import com.vmh.kubetool.data.models.Post

class PostItemDiffCallback(
    var oldPosts: List<Post>,
    var newPosts: List<Post>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldPosts.size

    override fun getNewListSize(): Int = newPosts.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldPosts[oldItemPosition].id == newPosts[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldPosts[oldItemPosition].equals(newPosts[newItemPosition])
    }
}