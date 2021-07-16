package com.timife.a911.emergencySearch.di

import androidx.lifecycle.ViewModel
import com.timife.a911.di.modules.ViewModelKey
import com.timife.a911.emergencySearch.ui.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class SearchModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindViewModel(viewmodel: SearchViewModel) : ViewModel
}