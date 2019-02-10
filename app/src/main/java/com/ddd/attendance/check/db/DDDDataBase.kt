package com.ddd.attendance.check.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ddd.attendance.check.db.dao.UserDao
import com.ddd.attendance.check.db.entity.User

@Database(version = 1, entities = [User::class], exportSchema = false)

abstract class DDDDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
}