package com.timife.a911.createProfile.di

import androidx.lifecycle.ViewModel
import com.timife.a911.createProfile.ui.CreateProfileViewModel
import com.timife.a911.di.modules.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class CreateProfileModule {

    @Binds
    @IntoMap
    @ViewModelKey(CreateProfileViewModel::class)
    abstract fun bindViewModel(viewmodel: CreateProfileViewModel): ViewModel
}