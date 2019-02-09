package com.ddd.attendance.check.di.module

import com.ddd.attendance.check.data.Repository
import com.ddd.attendance.check.data.login.LoginRepository
import com.ddd.attendance.check.data.login.source.LoginLocalDataSource
import com.ddd.attendance.check.data.login.source.LoginRemoteDataSource
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @Provides
    fun bindLoginRepository(localDataSource: LoginLocalDataSource, remoteDataSource: LoginRemoteDataSource): Repository {
        return LoginRepository(localDataSource, remoteDataSource)
    }
}