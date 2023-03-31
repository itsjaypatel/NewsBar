package com.example.newsbar

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide

class NewsListAdapter(private val listener: NewsItemClicked): RecyclerView.Adapter<NewsViewHolder>() {

    private val items: ArrayList<News> = ArrayList();

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)
        val viewHolder = NewsViewHolder(view)
        view.setOnClickListener{
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currItem = items[position]
        holder.titleView.text = currItem.title
        holder.authorView.text = currItem.author
        Glide.with(holder.itemView.context).load(currItem.urlToImage).into(holder.newsImageView)

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateNews(availableNews: ArrayList<News>){
        items.clear()
        items.addAll(availableNews)

        notifyDataSetChanged()
    }
}
class NewsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val titleView: TextView = itemView.findViewById(R.id.title)
    val authorView: TextView = itemView.findViewById(R.id.author)
    val newsImageView: ImageView = itemView.findViewById(R.id.newsImage)
}

interface NewsItemClicked{
    fun onItemClicked(item: News)
}
