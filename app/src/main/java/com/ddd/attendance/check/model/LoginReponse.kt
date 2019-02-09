package com.ddd.attendance.check.model

data class LoginResponse(
        val accessToken: String,
        val refreshToken: String,
        val status: String,
        val user: User,
        val message: String? = null
)

data class User(
    val account: String,
    val id: Int,
    val name: String
)