package com.ddd.attendance.check.data.source.user

import com.ddd.attendance.check.db.DDDDataBase
import com.ddd.attendance.check.db.entity.User
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(private val dddDataBase: DDDDataBase) {

    suspend fun saveUser(user: User) {
        dddDataBase.userDao().addUser(user)
    }
    fun deleteUser(user: User){
        dddDataBase.userDao().deleteUser(user)
    }
    fun getUsers(): List<User> {
        return dddDataBase.userDao().getUsers()
    }
}