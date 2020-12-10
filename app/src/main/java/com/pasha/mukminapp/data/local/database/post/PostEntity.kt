package com.pasha.mukminapp.data.local.database.post

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post")
data class PostEntity (
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val slug: String,
    val title: String,
    val thumnail: String,
    val language: String,
    val category: String,
    val content: String
)