package com.noahtownsend.hackernewsclient.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.noahtownsend.hackernewsclient.Story
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class HackerNewsStoryService {
    companion object {
        private var client: HackerNewsClient? = null

        private fun buildClient(): HackerNewsClient {
            return Retrofit.Builder()
                .baseUrl("https://example.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(HackerNewsClient::class.java)
        }
    }

    fun getTopStories(): LiveData<List<Story>> {
        client = client ?: buildClient()
        val result = MutableLiveData<List<Story>>(ArrayList<Story>())

        CoroutineScope(Dispatchers.IO).launch {
            val storyIds = client!!.getTopStoryIds()
            for (id in storyIds) {
                CoroutineScope(Dispatchers.IO).launch {
                    val story = client!!.getStoryById(id.toInt())
                    (result.value as MutableList<Story>).add(story)
                    result.postValue(result.value)
                }
            }
        }

        return result
    }

}