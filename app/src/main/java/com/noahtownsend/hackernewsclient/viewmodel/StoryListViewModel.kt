package com.noahtownsend.hackernewsclient.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.noahtownsend.hackernewsclient.Story
import com.noahtownsend.hackernewsclient.SystemValuesUtil
import com.noahtownsend.hackernewsclient.api.HackerNewsStoryService
import com.noahtownsend.hackernewsclient.datastore.HackerNewsStoryDatastore
import com.noahtownsend.hackernewsclient.db.StoryDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StoryListViewModel(storyDAO: StoryDAO) : ViewModel() {
    private val datastore = HackerNewsStoryDatastore(storyDAO, HackerNewsStoryService())
    fun getStories(context: Context): LiveData<List<Story>> {
        return datastore.getTopStories(context)
    }

    fun saveStories(context: Context, stories: List<Story>) {
        if (SystemValuesUtil.isConnectedToInternet(context)) {
            CoroutineScope(Dispatchers.IO).launch {
                datastore.updateStories(stories)
            }
        }
    }
}

class StoryListViewModelFactory(private val storyDAO: StoryDAO): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StoryListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StoryListViewModel(storyDAO) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}