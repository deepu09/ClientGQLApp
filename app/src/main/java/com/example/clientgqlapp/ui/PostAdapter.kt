package com.example.clientgqlapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.clientgqlapp.R
import com.example.clientgqlapp.UserQuery

/**
 * @author vkaja
 */
class PostAdapter : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private var posts : List<UserQuery.Post?>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val itemView =  LayoutInflater.from(parent.context).inflate(R.layout.post_row, parent, false)
        return PostViewHolder(itemView = itemView)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        posts?.let {
            holder.bindPost(it[position])
        }
    }

    override fun getItemCount(): Int {
        return posts?.size ?: 0
    }

    fun setPosts(userPosts : List<UserQuery.Post?>) {
        posts = userPosts
        notifyDataSetChanged()
    }

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val commentView: TextView = itemView.findViewById(R.id.comment_view)

        fun bindPost(post: UserQuery.Post?) {
            post?.let {
                commentView.text = it.comment
            }
        }
    }
}