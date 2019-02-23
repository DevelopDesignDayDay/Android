package com.ddd.attendance.check.data.source.user

import com.ddd.attendance.check.api.ApiService
import com.ddd.attendance.check.model.BaseResponse
import retrofit2.Response
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun refreshToken(refreshToken: String): Response<BaseResponse> {
        return apiService.refreshToken(refreshToken).await()
    }
}