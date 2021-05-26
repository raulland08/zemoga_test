package com.zemoga.data.api.comment

import kotlinx.serialization.Serializable

@Serializable
data class CommentRaw (
    var id: Int,
    var postId: Int,
    var name: String,
    var body: String
)