package com.zemoga.data

import com.zemoga.data.api.comment.CommentServiceApi
import com.zemoga.data.api.post.PostRO
import com.zemoga.data.api.post.PostServiceApi
import com.zemoga.data.api.user.UserServiceApi
import com.zemoga.data.util.deleteAllPosts
import com.zemoga.data.util.findAll
import com.zemoga.domain.entity.CommentEntity
import com.zemoga.domain.entity.PostEntity
import com.zemoga.domain.entity.UserEntity
import io.realm.Realm

class PostsRepository(
    private val postApi: PostServiceApi,
    private val userApi: UserServiceApi,
    private val commentApi: CommentServiceApi,
    private val postsSource: PostsSource,
    private val usersSource: UsersSource,
    private val commentsSource: CommentsSource
) {
    suspend fun getPosts(): List<PostEntity> {
        try {
            val response = postApi.getPosts()
            val posts = postsSource.mapPosts(response)
            posts.let {
                savePostsToLocalDB(it)
                return it
            }
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getUsers(): List<UserEntity> {
        try {
            val response = userApi.getUsers()
            return usersSource.mapUsers(response)
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getComments(): List<CommentEntity> {
        try {
            val response = commentApi.getComments()
            return commentsSource.mapComments(response)
        } catch (e: Exception) {
            throw e
        }
    }

    private fun savePostsToLocalDB(postEntities: List<PostEntity>) {
        val iterator = postEntities.iterator()
        while (iterator.hasNext()) {
            val realmPostEntity = PostRO()
            realmPostEntity.fromPostEntity(iterator.next())

            try {
                Realm.getDefaultInstance().executeTransaction { r ->
                    r.insertOrUpdate(
                        realmPostEntity
                    )
                }
            } catch (e: Exception) {
                throw e
            }
        }
    }

    fun updatePostStatus(postEntity: PostEntity) {
        val realmPostEntity = PostRO()
        realmPostEntity.fromPostEntity(postEntity)
        try {
            Realm.getDefaultInstance().executeTransaction { r ->
                r.insertOrUpdate(
                    realmPostEntity
                )
            }
        } catch (e: Exception) {
            throw e
        }
    }

    fun getLocalPosts(): MutableList<PostEntity> {
        val posts = mutableListOf<PostEntity>()

        val realmPost = Realm.getDefaultInstance().findAll<PostRO>()
        realmPost?.forEach {
            val post = PostEntity()
            post.toPostEntity(it)
            posts.add(post)
        }
        return posts
    }

    fun deleteAll() {
        Realm.getDefaultInstance().deleteAllPosts<PostRO>()
    }
}