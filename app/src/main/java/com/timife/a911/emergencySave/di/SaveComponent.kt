package com.timife.a911.emergencySave.di

import com.timife.a911.emergencyHome.di.HomeComponent
import com.timife.a911.emergencySave.ui.SaveFragment
import dagger.Subcomponent


@Subcomponent(modules = [SaveModule::class])
interface SaveComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create(): SaveComponent
    }


    fun inject(fragment:SaveFragment)
}