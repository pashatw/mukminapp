package com.pasha.mukminapp.data.remote


import com.google.gson.annotations.SerializedName

data class PostRequest(
    @SerializedName("id")
    val id: String? = "",
    @SerializedName("slug")
    val slug: String? = "",
    @SerializedName("title")
    val title: Title? = Title(),
    @SerializedName("content")
    val content: Content? = Content(),
    @SerializedName("jetpack_featured_media_url")
    val thumnail: String? = ""
){
    data class Title(
        @SerializedName("rendered")
        val rendered: String? = ""
    )

    data class Content(
        @SerializedName("rendered")
        val rendered: String? = ""
    )
}