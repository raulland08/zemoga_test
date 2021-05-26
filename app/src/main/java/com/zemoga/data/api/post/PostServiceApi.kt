package com.zemoga.data.api.post

import retrofit2.http.*

interface PostServiceApi {

    @GET("posts")
    suspend fun getPosts(): List<PostRaw>
}