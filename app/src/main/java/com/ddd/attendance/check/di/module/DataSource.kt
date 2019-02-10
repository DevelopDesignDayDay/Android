package com.ddd.attendance.check.di.module

import com.ddd.attendance.check.api.ApiService
import com.ddd.attendance.check.data.source.login.LoginRemoteDataSource
import com.ddd.attendance.check.data.source.user.UserLocalDataSource
import com.ddd.attendance.check.db.DDDDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataSource {

    @Provides
    @Singleton
    fun userLocalDataSource(dddDataBase: DDDDataBase): UserLocalDataSource {
        return UserLocalDataSource(dddDataBase)
    }

    @Provides
    @Singleton
    fun loginRemoteDataSource(apiService: ApiService): LoginRemoteDataSource {
        return LoginRemoteDataSource(apiService)
    }
}