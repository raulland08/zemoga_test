package com.zemoga.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zemoga.R
import com.zemoga.domain.entity.CommentEntity

class CommentsAdapter(
    private val dataSet: List<CommentEntity>
) : RecyclerView.Adapter<CommentsAdapter.CommentViewHolder>() {

    class CommentViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val comment: TextView = v.findViewById(R.id.comment)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CommentViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.comments_item, viewGroup, false)

        return CommentViewHolder(v)
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.comment.text = dataSet[position].body
    }
}