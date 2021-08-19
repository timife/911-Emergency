package com.timife.a911.di

import android.content.Context
import com.timife.a911.MainActivity
import com.timife.a911.createProfile.ui.CreateProfileFragment
import com.timife.a911.di.modules.AppModule
import com.timife.a911.di.modules.BindModule
import com.timife.a911.di.modules.ViewModelBuilderModule
import com.timife.a911.emergencyHome.ui.HomeFragment
import com.timife.a911.emergencyPref.ui.PrefFragment
import com.timife.a911.emergencySave.ui.SaveFragment
import com.timife.a911.emergencySearch.ui.SearchFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class,BindModule::class,ViewModelBuilderModule::class])
interface EmergencyComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): EmergencyComponent
    }

    fun inject(fragment: HomeFragment)
    fun inject(fragment: SearchFragment)
    fun inject(fragment:SaveFragment)
    fun inject(fragment:PrefFragment)
    fun inject(fragment:CreateProfileFragment)


//    fun inject(activity: MainActivity)

}

