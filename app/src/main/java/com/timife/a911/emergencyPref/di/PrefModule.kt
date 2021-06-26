package com.timife.a911.emergencyPref.di

//import com.timife.a911.di.modules.ViewModelKey
import androidx.lifecycle.ViewModel
import com.timife.a911.di.modules.ViewModelKey
import com.timife.a911.emergencyPref.ui.PrefViewModel
import com.timife.a911.emergencySave.ui.SaveViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

////
@Module
abstract class PrefModule {

    @Binds
    @IntoMap
    @ViewModelKey(PrefViewModel::class)
    abstract fun bindViewModel(viewmodel: PrefViewModel): ViewModel
}