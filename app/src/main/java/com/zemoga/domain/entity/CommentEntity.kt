package com.zemoga.domain.entity

data class CommentEntity(
    var id: Int,
    var postId: Int,
    var name: String,
    var body: String
)