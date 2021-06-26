package com.timife.a911.emergencySave.di

import androidx.lifecycle.ViewModel
import com.timife.a911.di.modules.ViewModelKey
import com.timife.a911.emergencySave.ui.SaveViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SaveModule {

    @Binds
    @IntoMap
    @ViewModelKey(SaveViewModel::class)
    abstract fun bindViewModel(viewmodel: SaveViewModel): ViewModel
}