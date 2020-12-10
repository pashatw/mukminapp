package com.pasha.mukminapp.data.local.database.post

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pasha.mukminapp.data.remote.PostRequest
import java.lang.reflect.Type
import java.util.*

class PostGson {
    var gson = Gson()

    @TypeConverter
    fun stringToSomeObjectList(data: String?): List<PostRequest?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type =
            object : TypeToken<List<PostRequest?>?>() {}.type
        return gson.fromJson<List<PostRequest?>>(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<PostRequest?>?): String? {
        return gson.toJson(someObjects)
    }
}