package com.zemoga.data

import com.zemoga.data.api.user.UserRaw
import com.zemoga.domain.entity.PostEntity
import com.zemoga.domain.entity.UserEntity

class UsersSource {

    fun mapUsers(usersRaw: List<UserRaw>): List<UserEntity> {

        val userEntityList = mutableListOf<UserEntity>()

        usersRaw.forEach { userRaw ->
            val userEntity = UserEntity(
                id = userRaw.id,
                name = userRaw.name,
                email = userRaw.email,
                phone = userRaw.phone,
                website = userRaw.website
            )

            userEntityList.add(userEntity)
        }

        return userEntityList
    }
}