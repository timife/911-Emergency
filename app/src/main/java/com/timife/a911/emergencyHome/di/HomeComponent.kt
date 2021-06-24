package com.timife.a911.emergencyHome.di

import com.timife.a911.emergencyHome.ui.HomeFragment
import dagger.Subcomponent


@Subcomponent(modules = [HomeModule::class])
interface HomeComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create(): HomeComponent
    }

    fun inject(fragment: HomeFragment)

}