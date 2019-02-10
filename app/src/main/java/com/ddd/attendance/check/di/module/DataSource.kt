package com.ddd.attendance.check.di.module

import com.ddd.attendance.check.api.ApiService
import com.ddd.attendance.check.data.login.source.LoginLocalDataSource
import com.ddd.attendance.check.data.login.source.LoginRemoteDataSource
import com.ddd.attendance.check.db.DDDDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataSource {

    @Provides
    @Singleton
    fun loginLocalDataSource(dddDataBase: DDDDataBase): LoginLocalDataSource {
        return LoginLocalDataSource(dddDataBase)
    }

    @Provides
    @Singleton
    fun loginRemoteDataSource(apiService: ApiService): LoginRemoteDataSource {
        return LoginRemoteDataSource(apiService)
    }
}