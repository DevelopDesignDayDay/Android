package com.ddd.attendance.check.di.module

import com.ddd.attendance.check.api.ApiService
import com.ddd.attendance.check.data.LoginRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @Provides
    fun bindLoginRepository(apiService: ApiService): LoginRepository {
        return LoginRepository(apiService)
    }
}