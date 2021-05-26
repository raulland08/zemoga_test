package com.zemoga.domain.entity

import com.zemoga.data.api.post.PostRO

data class PostEntity(
    var id: Int? = null,
    var userId: Int? = null,
    var title: String? = null,
    var body: String? = null,
    var isRead: Boolean = false,
    var isFavorite: Boolean = false
) {
    fun toPostEntity(obj: PostRO) {
        id = obj.id!!
        userId = obj.userId
        title = obj.title
        body = obj.body
        isRead = obj.isRead!!
        isFavorite = obj.isFavorite!!
    }
}