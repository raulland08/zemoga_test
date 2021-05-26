package com.zemoga.data

import com.zemoga.data.api.comment.CommentRaw
import com.zemoga.domain.entity.CommentEntity

class CommentsSource {

    fun mapComments(commentsRaw : List<CommentRaw>): List<CommentEntity> {

        val commentEntityList = mutableListOf<CommentEntity>()

        commentsRaw.forEach { commentRaw ->
            val commentEntity = CommentEntity(
                id = commentRaw.id,
                postId = commentRaw.postId,
                name = commentRaw.name,
                body = commentRaw.body
            )

            commentEntityList.add(commentEntity)
        }

        return commentEntityList
    }
}