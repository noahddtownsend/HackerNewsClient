package com.noahtownsend.hackernewsclient.api

import com.noahtownsend.hackernewsclient.Story
import retrofit2.http.GET
import retrofit2.http.Path

interface HackerNewsClient {
    companion object {
        // Base URL requires authentication and endpoints don't, no documented authentication method found
        private const val BASE_URL = "https://hacker-news.firebaseio.com/v0/"
    }

    @GET("$BASE_URL/item/{id}.json")
    suspend fun getStoryById(@Path("id") id: Int): Story

    @GET("$BASE_URL/topstories.json")
    suspend fun getTopStoryIds(): ArrayList<String>

}