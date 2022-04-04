package com.noahtownsend.hackernewsclient.datastore

import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import com.noahtownsend.hackernewsclient.api.HackerNewsStoryService
import com.noahtownsend.hackernewsclient.Story
import com.noahtownsend.hackernewsclient.SystemValuesUtil
import com.noahtownsend.hackernewsclient.db.StoryDAO

open class HackerNewsStoryDatastore(private val storyDAO: StoryDAO, private val hackerNewsStoryService: HackerNewsStoryService) {
    fun getTopStories(context: Context): LiveData<List<Story>> {
        return if (isConnectedToInternet(context)) {
            hackerNewsStoryService.getTopStories()
        } else {
            storyDAO.getAll()
        }
    }

    fun updateStories(stories: List<Story>) {
        storyDAO.deleteAll()
        storyDAO.insert(stories)
    }

    fun isConnectedToInternet(context: Context): Boolean {
        return SystemValuesUtil.isConnectedToInternet(context)
    }
}