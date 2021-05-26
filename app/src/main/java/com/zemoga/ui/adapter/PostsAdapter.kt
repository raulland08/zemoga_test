package com.zemoga.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.zemoga.R
import com.zemoga.domain.entity.PostEntity

class PostsAdapter(
    private val clickListener: OnPostClickListener,
    private val dataSet: List<PostEntity>
) : RecyclerView.Adapter<PostsAdapter.PostsViewHolder>() {

    class PostsViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val title: TextView = v.findViewById(R.id.title)
        val unreadIndicator: ImageView = v.findViewById(R.id.unread_indicator)
        val starIcon: ImageView = v.findViewById(R.id.star_icon)
        val container: CardView = v.findViewById(R.id.card)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): PostsViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.posts_item, viewGroup, false)

        return PostsViewHolder(v)
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        val res = holder.itemView.context.resources

        val item = dataSet[position]

        holder.title.text = dataSet[position].title
        holder.unreadIndicator.visibility = if (dataSet[position].isRead!!) View.GONE else View.VISIBLE
        holder.starIcon.visibility = if (dataSet[position].isFavorite!!) View.VISIBLE else View.GONE

        holder.container.setOnClickListener {
            clickListener.onOptionClicked(item)
        }
    }

    interface OnPostClickListener {
        fun onOptionClicked(option: PostEntity)
    }

}