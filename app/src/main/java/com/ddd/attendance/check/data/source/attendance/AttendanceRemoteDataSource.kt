package com.ddd.attendance.check.data.source.attendance

import com.ddd.attendance.check.api.ApiService
import com.ddd.attendance.check.model.Attendance
import retrofit2.Response

class AttendanceRemoteDataSource(private val apiService: ApiService) {
    suspend fun attendanceStart(): Response<Attendance> {
        return apiService.attendsStart().await()
    }

    suspend fun attendsEnd(): Response<Attendance> {
        return apiService.attendsEnd().await()
    }
}