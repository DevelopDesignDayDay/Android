package com.ddd.attendance.check.di.module

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.ddd.attendance.check.DDDApplication
import com.ddd.attendance.check.db.DDDDataBase
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

    @Provides
    @Singleton
    fun provideDataBase(application: DDDApplication): DDDDataBase {
        return Room.databaseBuilder(application, DDDDataBase::class.java, "DDDDataBase").build()
    }
}