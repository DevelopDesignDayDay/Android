package com.ddd.attendance.check.model

data class Attendance(
    val expire: String? = "",
    val number: Int? = 0,
    val status: String? = "",
    val message: String? = ""
)