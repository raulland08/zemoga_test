package com.zemoga.domain.usecase

import com.zemoga.data.PostsRepository
import com.zemoga.domain.entity.PostEntity

class DeleteAllPostsUseCase(
    private val postsRepository: PostsRepository)
{
    operator fun invoke() {
        postsRepository.deleteAll()
    }
}
