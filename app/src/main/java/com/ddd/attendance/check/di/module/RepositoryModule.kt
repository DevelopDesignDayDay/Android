package com.ddd.attendance.check.di.module

import com.ddd.attendance.check.data.Repository
import com.ddd.attendance.check.data.repository.LoginRepository
import com.ddd.attendance.check.data.repository.UserRepository
import com.ddd.attendance.check.data.source.login.LoginRemoteDataSource
import com.ddd.attendance.check.data.source.user.UserLocalDataSource
import com.ddd.attendance.check.data.source.user.UserRemoteDataSource
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @Provides
    fun bindLoginRepository(remoteDataSource: LoginRemoteDataSource): Repository {
        return LoginRepository(remoteDataSource)
    }

    @Provides
    fun bindUserRepository(localDataSource: UserLocalDataSource, remoteDataSource: UserRemoteDataSource): Repository {
        return UserRepository(localDataSource, remoteDataSource)
    }
}