package com.timife.a911.di.modules

import com.timife.a911.di.qualifiers.DefaultDispatcher
import com.timife.a911.di.qualifiers.IoDispatcher
import com.timife.a911.di.qualifiers.MainDispatcher
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
object DispatcherModule {

    @DefaultDispatcher
    @Provides
    fun provideDefaultDispatcher():CoroutineDispatcher = Dispatchers.Default

    @IoDispatcher
    @Provides
    fun provideIoDispatcher():CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @Provides
    fun provideMainDispatcher():CoroutineDispatcher = Dispatchers.Main


}