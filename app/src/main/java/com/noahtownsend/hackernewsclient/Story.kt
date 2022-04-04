package com.noahtownsend.hackernewsclient

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "story")
class Story(
    val by: String,
    val descendants: Int,
    @PrimaryKey
    val id: Int,
    val score: Int,
    val time: Long,
    val title: String,
    val type: String,
    val url: String
)