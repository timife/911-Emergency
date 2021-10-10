package com.timife.a911.di

import android.content.Context
import com.timife.a911.MainActivity
import com.timife.a911.SplashActivity
import com.timife.a911.createProfile.ui.CreateProfileFragment
import com.timife.a911.di.modules.*
import com.timife.a911.emergencyHome.ui.HomeFragment
import com.timife.a911.emergencyHome.ui.HomeFragmentCategory
import com.timife.a911.emergencyPref.ui.PrefFragment
import com.timife.a911.emergencySave.ui.SaveFragment
import com.timife.a911.emergencySearch.ui.SearchFragment
import com.timife.a911.ui.main.SplashFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [DatabaseModule::class, DataSourcesModule::class, ViewModelBuilderModule::class, DispatcherModule::class, RepositoryModule::class, ViewModelModule::class])
interface EmergencyComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): EmergencyComponent
    }

    fun inject(fragment: HomeFragment)
    fun inject(fragment: SearchFragment)
    fun inject(fragment: SaveFragment)
    fun inject(fragment: SplashFragment)
    fun inject(fragment: PrefFragment)
    fun inject(fragment: CreateProfileFragment)
    fun inject(fragment: HomeFragmentCategory)
    fun inject(activity: MainActivity)
    fun inject(activity: SplashActivity)

}

