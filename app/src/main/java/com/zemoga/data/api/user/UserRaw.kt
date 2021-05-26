package com.zemoga.data.api.user

import kotlinx.serialization.Serializable

@Serializable
data class UserRaw (
    var id: Int,
    var name: String,
    var username: String,
    var email: String,
    var address: AddressRaw,
    var phone: String,
    var website: String,
    var company: CompanyRaw
)

@Serializable
data class AddressRaw (
    var street: String,
    var suite: String,
    var city: String,
    var zipcode: String,
    var geo: GeoRaw
)

@Serializable
data class GeoRaw(
    var lat: String,
    var lng: String
)

@Serializable
data class CompanyRaw(
    var name: String,
    var catchPhrase: String,
    var bs: String
)