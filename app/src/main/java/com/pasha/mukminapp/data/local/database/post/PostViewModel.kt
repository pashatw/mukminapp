package com.pasha.mukminapp.data.local.database.post

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class PostViewModel constructor(application: Application) : AndroidViewModel(application) {
    private val repository : PostRepository = PostRepository(application)

    fun getAll(language: String): LiveData<List<PostEntity>> {
        return repository.getAll(language)
    }

    fun getById(id: String): LiveData<PostEntity> {
        return repository.getById(id)
    }

    fun insert(data: PostEntity){
        repository.insert(data)
    }

    fun updateFlag(entity: PostEntity){
        repository.updateFlag(entity)
    }

}