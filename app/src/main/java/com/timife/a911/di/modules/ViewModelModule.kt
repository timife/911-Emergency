package com.timife.a911.di.modules

import androidx.lifecycle.ViewModel
import com.timife.a911.createProfile.ui.CreateProfileViewModel
import com.timife.a911.emergencyHome.ui.dialogs.CallOptionViewModel
import com.timife.a911.emergencyHome.ui.HomeViewModel
import com.timife.a911.emergencyPref.ui.PrefViewModel
import com.timife.a911.emergencySave.ui.SaveViewModel
import com.timife.a911.emergencySearch.ui.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewmodel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PrefViewModel::class)
    abstract fun bindPrefViewModel(viewmodel: PrefViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SaveViewModel::class)
    abstract fun bindSaveViewModel(viewmodel: SaveViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(viewmodel: SearchViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreateProfileViewModel::class)
    abstract fun bindProfileViewModel(viewmodel: CreateProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CallOptionViewModel::class)
    abstract fun bindOptionViewModel(viewModel: CallOptionViewModel): ViewModel

}