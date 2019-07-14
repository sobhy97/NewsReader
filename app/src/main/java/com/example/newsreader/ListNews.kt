package com.example.newsreader

import android.app.AlertDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.newsreader.Adapter.ViewHolder.ListNewsAdapter
import com.example.newsreader.Common.Common
import com.example.newsreader.Interface.NewsService
import com.example.newsreader.Model.News
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_list_news.*
import retrofit2.Call
import retrofit2.Response

class ListNews : AppCompatActivity() {

    var  source =""
    var webUrl:String?=""

    lateinit var dialogg : AlertDialog
    lateinit var mService : NewsService
    lateinit var adapter : ListNewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_news)

        mService = Common.newsService
        dialogg = SpotsDialog(this)

        swipe_to_refresh.setOnRefreshListener { loadNews(source,true) }

        diagonal.setOnClickListener{

            val detail = Intent(baseContext,NewsDetails::class.java)
            detail.putExtra("webURL",webUrl)
            startActivity(detail)

        }
        list_news.setHasFixedSize(true)
        list_news.layoutManager = LinearLayoutManager(this)



        if (intent != null)
        {
            source = intent.getStringExtra("source")
            if (!source.isEmpty())
                loadNews(source,false)
        }
    }

    private fun loadNews(source: String?, isRefreshed: Boolean) {
        if (isRefreshed)
        {
            dialogg.show()
            mService.getNewsFromSources(Common.getNewsApi(source!!))
                    .enqueue(object : retrofit2.Callback<News>{
                        override fun onFailure(call: Call<News>, t: Throwable) {

                        }

                        override fun onResponse(call: Call<News>, response: Response<News>) {
                            dialogg.dismiss()

                                // get first article
                            Picasso.with(baseContext)
                                .load(response!!.body()!!.articles!![0].urlToImage)
                                .into(kenburns)

                            top_title.text = response!!.body()!!.articles!![0].title
                            top_author.text = response!!.body()!!.articles!![0].author

                            webUrl = response!!.body()!!.articles!![0].url

                            // load all articles
                            val removeFirstItem = response!!.body()!!.articles
                            // because we get first item to hot news , so we need to remove it
                            removeFirstItem!!.removeAt(0)

                            adapter = ListNewsAdapter(removeFirstItem!!,baseContext)
                            adapter.notifyDataSetChanged()
                            list_news.adapter=adapter




                        }


                    })
        }
        else
        {
            swipe_to_refresh.isRefreshing=true
            dialogg.show()
            mService.getNewsFromSources(Common.getNewsApi(source!!))
                .enqueue(object : retrofit2.Callback<News>{
                    override fun onFailure(call: Call<News>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<News>, response: Response<News>) {
                        swipe_to_refresh.isRefreshing = false

                        // get first article
                        Picasso.with(baseContext)
                            .load(response!!.body()!!.articles!![0].urlToImage)
                            .into(kenburns)

                        top_title.text = response!!.body()!!.articles!![0].title
                        top_author.text = response!!.body()!!.articles!![0].author

                        webUrl = response!!.body()!!.articles!![0].url

                        // load all articles
                        val removeFirstItem = response!!.body()!!.articles
                        // because we get first item to hot news , so we need to remove it
                        removeFirstItem!!.removeAt(0)

                        adapter = ListNewsAdapter(removeFirstItem!!,baseContext)
                        adapter.notifyDataSetChanged()
                        list_news.adapter=adapter




                    }


                })

        }



    }

}
