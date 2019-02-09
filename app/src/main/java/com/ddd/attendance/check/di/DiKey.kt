package com.ddd.attendance.check.di

import androidx.lifecycle.ViewModel
import com.ddd.attendance.check.data.Repository
import dagger.MapKey
import kotlin.reflect.KClass

@Target(
    AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class RepositoryKey(val value: KClass<out Repository>)