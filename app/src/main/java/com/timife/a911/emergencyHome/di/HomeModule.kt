package com.timife.a911.emergencyHome.di

import com.timife.a911.di.modules.ViewModelKey
import com.timife.a911.emergencyHome.ui.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class HomeModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindViewModel(viewmodel: HomeViewModel)
}