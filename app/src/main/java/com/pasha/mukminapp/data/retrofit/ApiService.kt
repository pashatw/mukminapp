package com.pasha.mukminapp.data.retrofit

import com.pasha.mukminapp.data.remote.HijriahRequest
import com.pasha.mukminapp.data.remote.PostRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @Headers("Accept: application/json")
    @GET("/wp-json/wp/v2/posts")
    fun getPost(
        @Query("tags") tags: Int
    ): Call<List<PostRequest>>

    @Headers("Accept: application/json")
    @GET("https://www.al-habib.info/utils/calendar/pengubah-kalender-hijriyah-v7.php?the_y=2020&the_m=12&the_d=11&the_conv=ctoh&lg=1")
    fun getHijriah(
        @Query("the_y") the_y: Int,
        @Query("the_m") the_m: Int,
        @Query("the_d") the_d: Int,
        @Query("the_conv") the_conv: String,
        @Query("lg") lg: Int,
    ): Call<HijriahRequest>

}