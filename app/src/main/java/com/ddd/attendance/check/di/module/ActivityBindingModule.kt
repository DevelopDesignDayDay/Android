package com.ddd.attendance.check.di.module

import com.ddd.attendance.check.di.qualifier.PerActivity
import com.ddd.attendance.check.ui.LoginActivity
import com.ddd.attendance.check.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBindingModule {
    @PerActivity
    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity

    @PerActivity
    @ContributesAndroidInjector
    abstract fun loginActivity(): LoginActivity

}