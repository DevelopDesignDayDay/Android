package com.ddd.attendance.check.data.repository

import com.ddd.attendance.check.data.Repository
import com.ddd.attendance.check.data.source.attendance.AttendanceRemoteDataSource
import com.ddd.attendance.check.model.Attendance
import com.ddd.attendance.check.model.AttendanceCheckResponse
import retrofit2.Response
import javax.inject.Inject

class AttendanceRepository @Inject constructor(private val attendanceRemoteDataSource: AttendanceRemoteDataSource) :
    Repository {
    suspend fun attendanceStart(): Response<Attendance> {
        return attendanceRemoteDataSource.attendanceStart()
    }

    suspend fun attendsEnd(): Response<Attendance> {
        return attendanceRemoteDataSource.attendanceEnd()
    }

    suspend fun attendanceCheck(userId: String, number: String): Response<AttendanceCheckResponse> {
        return attendanceRemoteDataSource.attendanceCheck(userId, number)
    }
}