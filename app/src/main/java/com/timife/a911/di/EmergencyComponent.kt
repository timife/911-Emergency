package com.timife.a911.di

import android.content.Context
import com.timife.a911.MainActivity
import com.timife.a911.di.modules.AppModule
import com.timife.a911.di.modules.BindModule
import com.timife.a911.di.modules.SubcomponentsModule
import com.timife.a911.di.modules.ViewModelBuilderModule
import com.timife.a911.emergencyHome.di.HomeComponent
import com.timife.a911.emergencyPref.di.PrefComponent
import com.timife.a911.emergencySave.di.SaveComponent
import com.timife.a911.emergencySearch.di.SearchComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class,BindModule::class,ViewModelBuilderModule::class,SubcomponentsModule::class])
interface EmergencyComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): EmergencyComponent
    }

    fun homeComponent(): HomeComponent.Factory

    fun searchComponent(): SearchComponent.Factory

    fun saveComponent(): SaveComponent.Factory

    fun prefComponent(): PrefComponent.Factory

    fun inject(activity: MainActivity)

}

