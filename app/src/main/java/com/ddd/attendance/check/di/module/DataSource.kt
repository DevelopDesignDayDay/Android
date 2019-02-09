package com.ddd.attendance.check.di.module

import com.ddd.attendance.check.DDDApplication
import com.ddd.attendance.check.api.ApiService
import com.ddd.attendance.check.data.login.source.LoginLocalDataSource
import com.ddd.attendance.check.data.login.source.LoginRemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataSource {

    @Provides
    @Singleton
    fun loginLocalDataSource(application: DDDApplication): LoginLocalDataSource {
        return LoginLocalDataSource(application)
    }

    @Provides
    @Singleton
    fun loginRemoteDataSource(apiService: ApiService): LoginRemoteDataSource {
        return LoginRemoteDataSource(apiService)
    }
}