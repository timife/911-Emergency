package com.timife.a911.di.modules

import com.timife.a911.emergencyHome.di.HomeComponent
import com.timife.a911.emergencyPref.di.PrefComponent
import com.timife.a911.emergencySave.di.SaveComponent
import com.timife.a911.emergencySearch.di.SearchComponent
import dagger.Module


@Module(
    subcomponents = [
        HomeComponent::class, SearchComponent::class, SaveComponent::class, PrefComponent::class]
)
object SubcomponentsModule