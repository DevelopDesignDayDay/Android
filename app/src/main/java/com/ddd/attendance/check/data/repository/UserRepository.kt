package com.ddd.attendance.check.data.repository

import com.ddd.attendance.check.data.Repository
import com.ddd.attendance.check.data.source.user.UserLocalDataSource
import com.ddd.attendance.check.data.source.user.UserRemoteDataSource
import com.ddd.attendance.check.db.entity.User
import com.ddd.attendance.check.model.BaseResponse
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource
) :
    Repository {
    suspend fun saveUsers(user: User) {
        userLocalDataSource.saveUser(user)
    }

    fun deleteUser(user: User) {
        userLocalDataSource.deleteUser(user)
    }

    fun getUsers(): List<User> {
        return userLocalDataSource.getUsers()
    }

    suspend fun refreshToken(refreshToken: String): Response<BaseResponse> {
        return userRemoteDataSource.refreshToken(refreshToken)
    }
}