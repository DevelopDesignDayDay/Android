package com.ddd.attendance.check.data.source.attendance

import com.ddd.attendance.check.api.ApiService
import com.ddd.attendance.check.model.Attendance
import com.ddd.attendance.check.model.AttendanceCheckResponse
import retrofit2.Response

class AttendanceRemoteDataSource(private val apiService: ApiService) {
    suspend fun attendanceStart(): Response<Attendance> {
        return apiService.attendsStart().await()
    }

    suspend fun attendanceEnd(): Response<Attendance> {
        return apiService.attendsEnd().await()
    }

    suspend fun attendanceCheck(userId: String, number: String): Response<AttendanceCheckResponse> {
        return apiService.attendsCheck(userId, number).await()
    }
}