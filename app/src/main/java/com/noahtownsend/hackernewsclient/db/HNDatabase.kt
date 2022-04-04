package com.noahtownsend.hackernewsclient.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.noahtownsend.hackernewsclient.Story

@Database(
    entities = [Story::class],
    version = 1,
    exportSchema = false
)
abstract class HNDatabase: RoomDatabase() {
    abstract fun storyDao(): StoryDAO

    companion object {
        @Volatile
        private var INSTANCE: HNDatabase? = null

        fun getDatabase(context: Context): HNDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HNDatabase::class.java,
                    "story_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                instance
            }
        }
    }
}