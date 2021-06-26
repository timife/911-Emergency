package com.timife.a911.emergencyPref.di

import com.timife.a911.emergencyHome.di.HomeComponent
import com.timife.a911.emergencyPref.ui.PrefFragment
import dagger.Subcomponent

@Subcomponent
interface PrefComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create(): PrefComponent
    }

    fun inject(fragment:PrefFragment)

}