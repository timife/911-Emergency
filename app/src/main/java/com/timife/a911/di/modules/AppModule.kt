package com.timife.a911.di.modules

import android.content.Context
import androidx.room.Room
import com.timife.a911.data.source.EmergencyDataSource
import com.timife.a911.data.source.local.EmergencyDatabase
import com.timife.a911.data.source.local.EmergencyLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton


@Module
object AppModule {
//    @Singleton
//    @Provides
//    fun provideEmergencyLocalDataSource(
//            database: EmergencyDatabase,
//            ioDispatcher: CoroutineDispatcher
//    ): EmergencyDataSource{
//        return EmergencyLocalDataSource(
//               database.emergencyDao(),ioDispatcher
//        )
//    }
    @Singleton
    @Provides
    fun provideDatabase(context:Context):
            EmergencyDatabase{
        return Room.databaseBuilder(
                context.applicationContext,
                EmergencyDatabase::class.java,
                "EmergencyInfo.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO

}