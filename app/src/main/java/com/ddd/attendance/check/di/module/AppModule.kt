package com.ddd.attendance.check.di.module

import android.content.Context
import com.ddd.attendance.check.DDDApplication
import com.ddd.attendance.check.di.qualifier.ForApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    @ForApplication
    fun provideApplicationContext(application: DDDApplication): Context {
        return application.applicationContext
    }
}