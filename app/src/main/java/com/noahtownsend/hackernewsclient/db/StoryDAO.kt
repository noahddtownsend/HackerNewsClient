package com.noahtownsend.hackernewsclient.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.noahtownsend.hackernewsclient.Story

@Dao
interface StoryDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    @JvmSuppressWildcards
    fun insert(storyList: List<Story>)

    @Query("SELECT * FROM story")
    fun getAll(): LiveData<List<Story>>

    @Query("DELETE FROM story")
    fun deleteAll()
}