package com.example.newsreader.Common

import com.example.newsreader.Interface.NewsService
import com.example.newsreader.Retrofit.RetrofitClient

object Common{

    val BASE_URL = "https://newsapi.org/"
    val API_KEY = "29af8ef68aba4d10a25b1a9b8f7d1f6e"

    val newsService:NewsService
        get() = RetrofitClient.getClient(BASE_URL).create(NewsService::class.java)

    fun getNewsApi(source:String):String{
        val apiUrl = StringBuilder("https://newsapi.org/v2/top-headlines?sources=")
            .append(source)
            .append("&apiKey=")
            .append(API_KEY)
            .toString()
        return apiUrl
    }
}