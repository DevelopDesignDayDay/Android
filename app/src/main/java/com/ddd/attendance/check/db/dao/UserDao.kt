package com.ddd.attendance.check.db.dao

import androidx.room.*
import com.ddd.attendance.check.db.entity.User

@Dao
interface UserDao {
    @Query("SELECT * FROM USER")
    fun getUsers(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(vararg user: User)


    @Delete()
    fun deleteUser(user: User)
}
