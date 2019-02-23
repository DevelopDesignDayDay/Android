@file:Suppress("UNCHECKED_CAST")

package com.ddd.attendance.check.di.module

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.ddd.attendance.check.DDDApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharedPreferencesModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(context: DDDApplication): SharedPreferences {
        return context.getSharedPreferences(NAME_SHARED, MODE_PRIVATE)
    }

    companion object {
        const val KEY_STATUS = "status"
        const val NAME_SHARED = "ddd_admin_status"
        const val KEY_NUMBER = "number"
    }
}