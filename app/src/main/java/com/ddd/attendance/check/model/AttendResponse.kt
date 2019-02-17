package com.ddd.attendance.check.model

data class Attendance(
    val expire: String? = "",
    val number: Int? = 0,
    val status: String? = "",
    val message: String? = "이미 출석 체크가 시작되었습니다."
)