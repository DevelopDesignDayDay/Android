package com.ddd.attendance.check.model

data class BaseResponse(
    val status: String = "",
    val accessToken: String? = "",
    val refreshToken: String? = "",
    val message: String? = ""
)