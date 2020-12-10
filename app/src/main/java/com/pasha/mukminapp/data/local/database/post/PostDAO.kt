package com.pasha.mukminapp.data.local.database.post

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PostDAO {
    @Query("SELECT * from post where language=:language")
    fun getAllPost(language: String): LiveData<List<PostEntity>>

    @Query("SELECT * from post WHERE id =:id")
    fun getById(id: String): LiveData<PostEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: PostEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(data: PostEntity)
}