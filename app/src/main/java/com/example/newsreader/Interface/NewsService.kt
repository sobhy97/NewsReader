package com.example.newsreader.Interface

import com.example.newsreader.Model.News
import com.example.newsreader.Model.Website
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface NewsService {

    @get:GET("v2/sources?apiKey=29af8ef68aba4d10a25b1a9b8f7d1f6e")
    val sources: Call<Website>

    @GET
    fun getNewsFromSources(@Url url:String) : Call<News>
}