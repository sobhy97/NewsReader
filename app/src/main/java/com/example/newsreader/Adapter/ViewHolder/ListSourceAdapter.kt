package com.example.newsreader.Adapter.ViewHolder

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.newsreader.Interface.itemClickListener
import com.example.newsreader.ListNews
import com.example.newsreader.Model.Website
import com.example.newsreader.R

class ListSourceAdapter(private val context:Context,private val website:Website):RecyclerView.Adapter<ListSourceViewHolder>()
{
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ListSourceViewHolder {
        val inflater = LayoutInflater.from(p0!!.context)
        val itemView = inflater.inflate(R.layout.news_layout,p0,false)
        return ListSourceViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return website.sources!!.size
    }

    override fun onBindViewHolder(p0: ListSourceViewHolder, p1: Int) {

        p0!!.source_title.text = website.sources!![p1].name

        p0.setItemClickListener(object : itemClickListener
        {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(context,ListNews::class.java)
                intent.putExtra("source",website.sources!![position].id)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)

            }
        })

    }

}