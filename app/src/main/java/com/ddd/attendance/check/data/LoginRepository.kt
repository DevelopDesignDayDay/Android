package com.ddd.attendance.check.data

import com.ddd.attendance.check.api.ApiService
import com.ddd.attendance.check.model.LoginResponse
import retrofit2.Response

class LoginRepository(private val apiService: ApiService) {
    suspend fun login(id: String, password: String): Response<LoginResponse> {
        return apiService.login(id, password).await()
    }
}