package com.zemoga.data.api.user

import retrofit2.http.GET

interface UserServiceApi {

    @GET("users")
    suspend fun getUsers(): List<UserRaw>
}