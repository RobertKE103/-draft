package com.example.networkinteractions.ui.main

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.networkinteractions.R
import com.example.networkinteractions.databinding.ItemNewsRvBinding
import com.example.networkinteractions.modules.Article

class NewsListAdapter :
    PagingDataAdapter<Article, NewsListAdapter.NewsViewHolder>(ArticleDiffCallback) {

    class NewsViewHolder(
        private val viewBinding: ItemNewsRvBinding,
        private val context: Context,
    ) : ViewHolder(viewBinding.root) {
        fun bind(article: Article?) {
            with(viewBinding) {
                Glide.with(context)
                    .load(article?.urlToImage)
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(image)

                title.text = article?.title ?: ""
                content.text = article?.content
            }
        }
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNewsRvBinding.inflate(inflater, parent, false)
        return NewsViewHolder(binding, parent.context)
    }
}


private object ArticleDiffCallback : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.title == newItem.title
    }

}