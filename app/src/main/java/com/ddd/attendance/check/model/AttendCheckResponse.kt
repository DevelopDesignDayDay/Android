package com.ddd.attendance.check.model

data class AttendanceCheckResponse(
    val status: String? = "",
    val userId: Int? = 0,
    val message: String? = ""
)