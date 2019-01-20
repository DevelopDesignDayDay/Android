package com.ddd.attendance.check.di.module

import com.ddd.attendance.check.di.scope.ActivityScoped
import com.ddd.attendance.check.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity

}