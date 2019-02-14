package com.ddd.attendance.check.data.repository

import com.ddd.attendance.check.data.Repository
import com.ddd.attendance.check.data.source.login.LoginRemoteDataSource
import com.ddd.attendance.check.model.LoginResponse
import retrofit2.Response
import javax.inject.Inject

class LoginRepository @Inject constructor(
        private val loginRemoteDataSource: LoginRemoteDataSource
) : Repository {

    suspend fun login(id: String, password: String): Response<LoginResponse> {
        return loginRemoteDataSource.login(id, password)
    }
}