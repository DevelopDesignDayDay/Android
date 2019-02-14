package com.ddd.attendance.check.data.repository

import com.ddd.attendance.check.data.Repository
import com.ddd.attendance.check.data.source.user.UserLocalDataSource
import com.ddd.attendance.check.db.entity.User
import javax.inject.Inject

class UserRepository @Inject constructor(private val userLocalDataSource: UserLocalDataSource) :
    Repository {
    suspend fun saveUsers(user: User) {
        userLocalDataSource.saveUser(user)
    }

    fun getUsers(): List<User> {
        return userLocalDataSource.getUsers()
    }
}