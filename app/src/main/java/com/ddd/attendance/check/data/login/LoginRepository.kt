package com.ddd.attendance.check.data.login

import com.ddd.attendance.check.data.Repository
import com.ddd.attendance.check.data.login.source.LoginLocalDataSource
import com.ddd.attendance.check.data.login.source.LoginRemoteDataSource
import com.ddd.attendance.check.model.LoginResponse
import retrofit2.Response
import javax.inject.Inject

class LoginRepository @Inject constructor(private val loginLocalDataSource: LoginLocalDataSource,
                                          private val remoteDataSource: LoginRemoteDataSource) : Repository {
    fun saveToken(token: String) {
        loginLocalDataSource.saveToken(token)
    }

    suspend fun login(id: String, password: String): Response<LoginResponse> {
        return remoteDataSource.login(id, password)
    }
}