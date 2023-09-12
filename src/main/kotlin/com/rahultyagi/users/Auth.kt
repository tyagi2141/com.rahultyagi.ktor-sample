package com.rahultyagi.users

import kotlinx.serialization.Serializable

@kotlinx.serialization.Serializable
data class SignUpParams(
    val name: String,
    val email: String,
    val password: String
)

@kotlinx.serialization.Serializable
data class SignInParams(
    val email: String,
    val password: String
)

@Serializable
data class AuthResponse(
    val data: AuthResponseData? = null,
    val errorMessage: String? = null
)

@Serializable
data class AuthResponseData(
    val id: Int,
    val name: String,
    val bio: String,
    val avatar: String? = null,
    val token: String,
    val followersCount: Int = 0,
    val followingCount: Int = 0
)
