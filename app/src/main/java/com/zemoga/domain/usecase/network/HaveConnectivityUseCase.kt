package com.zemoga.domain.usecase.network

import com.zemoga.data.network.NetworkRepository

class HaveConnectivityUseCase(private val networkRepository: NetworkRepository) {

    operator fun invoke(): Boolean {
       return networkRepository.hasInternetConnection()
    }

}