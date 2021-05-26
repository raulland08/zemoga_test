package com.zemoga.domain.usecase

import com.zemoga.data.PostsRepository
import com.zemoga.domain.entity.CommentEntity
import com.zemoga.domain.entity.PostEntity

class GetCommentsUseCase(
    private val postsRepository: PostsRepository)
{
    suspend operator fun invoke() : List<CommentEntity> = postsRepository.getComments()
}