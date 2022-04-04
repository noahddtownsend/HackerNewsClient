package com.noahtownsend.hackernewsclient

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.noahtownsend.hackernewsclient.api.HackerNewsStoryService
import com.noahtownsend.hackernewsclient.datastore.HackerNewsStoryDatastore
import com.noahtownsend.hackernewsclient.db.StoryDAO
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.internal.util.MockUtil.resetMock
import org.powermock.api.mockito.PowerMockito.*
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner


@RunWith(PowerMockRunner::class)
@PrepareForTest(
    SystemValuesUtil::class,
    HackerNewsStoryService::class,
    HackerNewsStoryDatastore::class
)
class HackerNewsStoryDatastoreTest {
    private val context: Context = mock(Context::class.java)
    private val storyDAO: StoryDAO = mock(StoryDAO::class.java)
    private val hackerNewsStoryService: HackerNewsStoryService = mock(HackerNewsStoryService::class.java)
    private lateinit var hackerNewsStoryDatastore: HackerNewsStoryDatastore

    val networkStories = MutableLiveData(
        ArrayList<Story>().apply {
            add(Story("network", 0, 0, 100, 1, "network", "story", "https://www.example.com/"))
        }
    )
    val localStories = MutableLiveData(
        ArrayList<Story>().apply {
            add(Story("local", 0, 0, 100, 1, "local", "story", "https://www.example.com/"))
        }
    )

    @Before
    fun beforeEach() {
        resetMock(storyDAO)
        hackerNewsStoryDatastore = spy(HackerNewsStoryDatastore(storyDAO, hackerNewsStoryService))
    }

    @Test
    fun getsFromApi_isOnline_success() {
        `when`(hackerNewsStoryService.getTopStories()).thenReturn(networkStories as LiveData<List<Story>>)
        doReturn(true).`when`(hackerNewsStoryDatastore).isConnectedToInternet(context)

        val data = hackerNewsStoryDatastore.getTopStories(context)

        assertEquals(data.value!![0].title, networkStories.value!![0].title)
    }

    @Test
    fun getsFromDatabase_isOnline_success() {
        `when`(storyDAO.getAll()).thenReturn(localStories as LiveData<List<Story>>)
        doReturn(false).`when`(hackerNewsStoryDatastore).isConnectedToInternet(context)

        val data = hackerNewsStoryDatastore.getTopStories(context)

        assertEquals(data.value!![0].title, localStories.value!![0].title)
    }
}