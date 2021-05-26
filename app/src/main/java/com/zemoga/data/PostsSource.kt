package com.zemoga.data

import com.zemoga.data.api.post.PostRaw
import com.zemoga.domain.entity.PostEntity

class PostsSource {

    fun mapPosts(postsRaw: List<PostRaw>): List<PostEntity> {

        val postEntityList = mutableListOf<PostEntity>()

        postsRaw.forEachIndexed { index, postRaw ->
            val postEntity = PostEntity(
                id = postRaw.id,
                userId = postRaw.userId,
                title = postRaw.title,
                body = postRaw.body,
                isRead = index !in 0..19,
                isFavorite = false
            )

            postEntityList.add(postEntity)
        }

        return postEntityList
    }
}