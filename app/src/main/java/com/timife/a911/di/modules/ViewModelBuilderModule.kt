package com.timife.a911.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.timife.a911.di.EmergencyViewModelFactory
import dagger.Binds
import dagger.MapKey
import dagger.Module
import kotlin.reflect.KClass

@Module
abstract class ViewModelBuilderModule {

    @Binds
    abstract fun bindViewModelFactory(
        factory: EmergencyViewModelFactory
    ): ViewModelProvider.Factory
}


@Target(
    AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)