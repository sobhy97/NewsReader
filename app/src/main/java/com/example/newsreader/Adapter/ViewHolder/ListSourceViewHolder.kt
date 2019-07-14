package com.example.newsreader.Adapter.ViewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.newsreader.Interface.itemClickListener
import kotlinx.android.synthetic.main.news_layout.view.*

class ListSourceViewHolder(itemview:View):RecyclerView.ViewHolder(itemview),View.OnClickListener{

    private lateinit var itemClick : itemClickListener
    var source_title = itemview.news_name

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