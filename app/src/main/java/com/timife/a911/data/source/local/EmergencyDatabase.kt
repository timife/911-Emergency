package com.timife.a911.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.timife.a911.data.EmergencyInfo

@Database(entities = [EmergencyInfo::class],version = 1,exportSchema =false)
abstract class EmergencyDatabase:RoomDatabase(){
    abstract val emergencyDao: EmergencyDao
}
