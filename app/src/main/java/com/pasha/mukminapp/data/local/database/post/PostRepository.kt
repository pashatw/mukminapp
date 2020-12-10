package com.pasha.mukminapp.data.local.database.post

import android.app.Application
import androidx.lifecycle.LiveData
import com.pasha.mukminapp.data.local.database.AppDatabase
import com.pasha.mukminapp.data.local.database.post.PostDAO

class PostRepository constructor(application: Application) {

    lateinit var dao : PostDAO

    init {
        val database = AppDatabase.getInstance(application)
        if (database != null) {
            dao = database.postDAO()
        }
    }

    fun getAll(language: String) : LiveData<List<PostEntity>> {
        return dao.getAllPost(language)
    }

    fun getById(id: String) : LiveData<PostEntity> {
        return dao.getById(id)
    }

    fun insert(entity: PostEntity) {
        val thread = object : Thread() {
            override fun run() {
                dao.insert(entity)
            }
        }
        thread.start()
    }

    fun updateFlag(entity: PostEntity) {
        val thread = object : Thread() {
            override fun run() {
                dao.update(entity)
            }
        }
        thread.start()
    }

}