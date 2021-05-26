package com.zemoga.data.api.post

import kotlinx.serialization.Serializable

@Serializable
data class PostRaw (
    var id: Int,
    var userId: Int,
    var title: String,
    var body: String
)