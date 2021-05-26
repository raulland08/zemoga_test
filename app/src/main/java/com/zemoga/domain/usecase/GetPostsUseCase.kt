package com.zemoga.domain.usecase

import com.zemoga.data.PostsRepository
import com.zemoga.domain.entity.PostEntity

class GetPostsUseCase(
    private val postsRepository: PostsRepository)
{
    suspend operator fun invoke() : List<PostEntity> {
        return if (postsRepository.getLocalPosts().size > 0) {
            postsRepository.getLocalPosts()
        } else {
            postsRepository.getPosts()
        }
    }
}
