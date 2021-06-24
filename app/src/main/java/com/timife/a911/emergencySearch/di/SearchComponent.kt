package com.timife.a911.emergencySearch.di

import com.timife.a911.emergencyHome.di.HomeComponent
import com.timife.a911.emergencySearch.ui.SearchFragment
import dagger.Subcomponent

@Subcomponent(modules = [SearchModule::class])
interface SearchComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create(): SearchComponent
    }

    fun inject(fragment:SearchFragment)

}