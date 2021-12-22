package com.timife.a911.di

import android.content.Context
import com.timife.a911.MainActivity
import com.timife.a911.createProfile.ui.CreateProfileFragment
import com.timife.a911.di.modules.*
import com.timife.a911.ui.emergencyHome.ui.dialogs.CallOptionDialog
import com.timife.a911.ui.emergencyHome.HomeFragment
import com.timife.a911.ui.emergencyHome.HomeFragmentCategory
import com.timife.a911.ui.EmergencyPref.PrefFragment
import com.timife.a911.ui.EmergencySave.SaveFragment
import com.timife.a911.ui.EmergencySearch.SearchFragment
import com.timife.a911.ui.splash.SplashFragment
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
    fun inject(fragment: CallOptionDialog)
    fun inject(activity: MainActivity)

}

