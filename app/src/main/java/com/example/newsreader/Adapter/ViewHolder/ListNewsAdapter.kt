package com.example.newsreader.Adapter.ViewHolder

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.newsreader.Common.ISO
import com.example.newsreader.Interface.itemClickListener
import com.example.newsreader.Model.Article
import com.example.newsreader.NewsDetails
import com.example.newsreader.R
import com.squareup.picasso.Picasso
import java.lang.Exception
import java.text.ParseException
import java.util.*

class ListNewsAdapter(val articleList:MutableList<Article>,private val context:Context):RecyclerView.Adapter<ListNewsViewHolder>()
{

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ListNewsViewHolder {
        val inflater = LayoutInflater.from(p0!!.context)
        val itemView = inflater.inflate(R.layout.display_layout,p0,false)
        return ListNewsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(p0: ListNewsViewHolder, p1: Int) {

       Picasso.with(context).load(articleList[p1].urlToImage).into(p0.article_image)

        if (articleList[p1].title!!.length>65)
        {
            p0.article_title.text=articleList[p1].title!!.substring(0,65)+"...."
        }
        else
        {
            p0.article_title.text=articleList[p1].title!!

        }
        if(articleList[p1].publishedAt != null)
        {
            var date:Date?=null
            try {
                date = ISO.parse(articleList[p1].publishedAt!!)
            }catch (ex:ParseException)
            {
                ex.printStackTrace()
            }
            p0.article_time.setReferenceTime(date!!.time)

        }

        // set event click
        p0.setItemClickListener(object : itemClickListener{
            override fun onClick(view: View, position: Int) {
                // web view
                val detail = Intent(context,NewsDetails::class.java)
                detail.putExtra("webURL",articleList[position].url)
                detail.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(detail)

            }

        })

    }


}