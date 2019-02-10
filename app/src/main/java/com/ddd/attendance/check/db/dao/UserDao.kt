package com.ddd.attendance.check.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ddd.attendance.check.db.entity.User

@Dao
interface UserDao {
    @Query("SELECT * FROM USER")
    fun getUsers(): List<User>

    @Insert
    suspend fun addUser(user: User)
}
