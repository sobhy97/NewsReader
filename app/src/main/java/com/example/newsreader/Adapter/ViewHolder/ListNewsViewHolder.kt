package com.example.newsreader.Adapter.ViewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.newsreader.Interface.itemClickListener
import kotlinx.android.synthetic.main.display_layout.view.*
import kotlinx.android.synthetic.main.news_layout.view.*

class ListNewsViewHolder(itemview: View): RecyclerView.ViewHolder(itemview), View.OnClickListener{

    private lateinit var itemClick : itemClickListener
    var article_title = itemview.article_title
    var article_time = itemview.article_time
    var article_image = itemview.article_image

    init {
        itemview.setOnClickListener(this)
    }

    fun setItemClickListener(itemClick:itemClickListener)
    {
        this.itemClick = itemClick
    }



    override fun onClick(v: View) {
        itemClick.onClick(v,adapterPosition)

    }



}
