package com.timife.a911.di.modules

import com.timife.a911.createProfile.ui.AuthRepository
import com.timife.a911.createProfile.ui.AuthRepositoryImpl
import com.timife.a911.data.repository.EmergencyRepository
import com.timife.a911.data.repository.EmergencyRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindRepository(repo: EmergencyRepositoryImpl): EmergencyRepository



}