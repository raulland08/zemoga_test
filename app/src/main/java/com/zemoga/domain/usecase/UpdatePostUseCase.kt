package com.zemoga.domain.usecase

import com.zemoga.data.PostsRepository
import com.zemoga.domain.entity.PostEntity

class UpdatePostUseCase(
    private val postsRepository: PostsRepository)
{
    operator fun invoke(post: PostEntity) {
        postsRepository.updatePostStatus(post)
    }
}
