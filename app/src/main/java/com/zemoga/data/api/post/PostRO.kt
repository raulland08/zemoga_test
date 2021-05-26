package com.zemoga.data.api.post

import com.zemoga.domain.entity.PostEntity
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
open class PostRO(
    @PrimaryKey var id: Int? = null,
    var userId: Int? = null,
    var title: String? = null,
    var body: String? = null,
    var isRead: Boolean? = null,
    var isFavorite: Boolean? = null
) : RealmObject() {
    fun fromPostEntity(obj: PostEntity) {
        id = obj.id
        userId = obj.userId
        title = obj.title
        body = obj.body
        isRead = obj.isRead
        isFavorite = obj.isFavorite
    }
}