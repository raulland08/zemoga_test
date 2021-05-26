package com.zemoga.data.api.comment

import retrofit2.http.GET

interface CommentServiceApi {

    @GET("comments")
    suspend fun getComments(): List<CommentRaw>
}