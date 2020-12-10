package com.pasha.mukminapp.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pasha.mukminapp.data.local.database.post.PostDAO
import com.pasha.mukminapp.data.local.database.post.PostEntity
import com.pasha.mukminapp.data.local.database.post.PostGson


@Database(
    entities = [
        PostEntity::class
    ],
    version = 5,
    exportSchema = false
)

@TypeConverters(
    PostGson::class,
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun postDAO(): PostDAO

    companion object {
        var instance: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase? {
            if (instance == null) {
                synchronized(AppDatabase::class) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, "mukminapp")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance
        }
    }
}