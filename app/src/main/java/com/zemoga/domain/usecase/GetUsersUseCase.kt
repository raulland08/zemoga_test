package com.zemoga.domain.usecase

import com.zemoga.data.PostsRepository
import com.zemoga.domain.entity.UserEntity

class GetUsersUseCase(
    private val postsRepository: PostsRepository)
{
    suspend operator fun invoke() : List<UserEntity> = postsRepository.getUsers()
}