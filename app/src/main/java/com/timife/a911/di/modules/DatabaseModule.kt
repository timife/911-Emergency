package com.timife.a911.di.modules

import android.content.Context
import androidx.room.Room
import com.timife.a911.data.source.local.EmergencyDao
import com.timife.a911.data.source.local.EmergencyDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {

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


}