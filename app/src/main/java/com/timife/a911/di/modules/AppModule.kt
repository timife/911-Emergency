package com.timife.a911.di.modules

import android.content.Context
import androidx.room.Room
import com.timife.a911.data.repository.CentralRepository
import com.timife.a911.data.repository.EmergencyRepository
import com.timife.a911.data.source.EmergencyDataSource
import com.timife.a911.data.source.local.EmergencyDao
import com.timife.a911.data.source.local.EmergencyDatabase
import com.timife.a911.data.source.local.EmergencyLocalDataSource
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton


@Module
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context):
            EmergencyDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            EmergencyDatabase::class.java,
            "EmergencyInfo.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideEmergencyDao(emergencyDatabase: EmergencyDatabase): EmergencyDao {
        return emergencyDatabase.emergencyDao
    }

    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO

    @Singleton
    @Provides
    fun provideRepository(datasource:EmergencyLocalDataSource,ioDispatcher: CoroutineDispatcher): EmergencyRepository{
        return CentralRepository(datasource,ioDispatcher)
    }

    @Singleton
    @Provides
    fun provideDataSource(emergencyDao: EmergencyDao,ioDispatcher: CoroutineDispatcher): EmergencyDataSource{
        return EmergencyLocalDataSource(emergencyDao, ioDispatcher)
    }

}