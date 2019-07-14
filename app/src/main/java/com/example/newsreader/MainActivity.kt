package com.example.newsreader

import android.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.example.newsreader.Adapter.ViewHolder.ListSourceAdapter
import com.example.newsreader.Common.Common
import com.example.newsreader.Interface.NewsService
import com.example.newsreader.Model.Website
import com.google.gson.Gson
import dmax.dialog.SpotsDialog
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {

    lateinit var layoutManager : LinearLayoutManager
    lateinit var mService : NewsService
    lateinit var adapter: ListSourceAdapter
    lateinit var dialog : AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //init cache db
        Paper.init(this)
        //init service
        mService = Common.newsService

        //init view
        swipe_refresh.setOnRefreshListener {
            loadWebSiteSources(true)
        }
        recycler_view.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = layoutManager

        dialog = SpotsDialog(this)
        loadWebSiteSources(false)
    }

    private fun loadWebSiteSources(isRefresh: Boolean) {

        if (!isRefresh)
        {
            val cache = Paper.book().read<String>("cache")
            if (cache != null && !cache.isBlank() && cache != "null" )
            {
                // read cache

                val Website = Gson().fromJson<Website>(cache,Website::class.java)
                adapter = ListSourceAdapter(baseContext,Website)
                adapter.notifyDataSetChanged()
                recycler_view.adapter = adapter
            }
            else
            { //load website and write cache
                dialog.show()
                // fetch data
                mService.sources.enqueue(object:retrofit2.Callback<Website>{

                    override fun onFailure(call: Call<Website>, t: Throwable) {
                        Toast.makeText(baseContext,"Failed",Toast.LENGTH_LONG).show()
                    }
                    override fun onResponse(call: Call<Website>, response: Response<Website>) {
                        adapter = ListSourceAdapter(baseContext,response!!.body()!!)
                        adapter.notifyDataSetChanged()
                        recycler_view.adapter = adapter
                        // save to cache
                        Paper.book().write("cache",Gson().toJson(response!!.body()!!))
                        dialog.dismiss()
                    }



                })


            }
        }
        else
        {
            swipe_refresh.isRefreshing=true
            //fetch new data
            mService.sources.enqueue(object:retrofit2.Callback<Website>{

                override fun onFailure(call: Call<Website>, t: Throwable) {
                    Toast.makeText(baseContext,"Failed",Toast.LENGTH_LONG).show()
                }
                override fun onResponse(call: Call<Website>, response: Response<Website>) {
                    adapter = ListSourceAdapter(baseContext,response!!.body()!!)
                    adapter.notifyDataSetChanged()
                    recycler_view.adapter = adapter
                    // save to cache
                    Paper.book().write("cache",Gson().toJson(response!!.body()!!))
                    swipe_refresh.isRefreshing=false
                }
            })

        }

    }

}
