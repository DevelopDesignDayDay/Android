package com.ddd.attendance.check.data.login.source

import com.ddd.attendance.check.db.DDDDataBase
import com.ddd.attendance.check.db.entity.User
import javax.inject.Inject

class LoginLocalDataSource @Inject constructor(private val dddDataBase: DDDDataBase) {

    suspend fun saveUser(user: User) {
        dddDataBase.userDao().addUser(user)
    }
}