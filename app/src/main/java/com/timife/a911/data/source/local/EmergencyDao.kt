package com.timife.a911.data.source.local

import androidx.room.*
import com.timife.a911.data.EmergencyInfo

@Dao
interface EmergencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: EmergencyInfo)

    @Delete
    suspend fun delete(item: EmergencyInfo)

    @Query("SELECT EXISTS ( SELECT 1 from emergency_info WHERE name = :name AND number = :phone AND type = :type AND location = :location) ")
    fun exists(
        name: String,
        phone: String,
        type: String,
        location: String
    ): Boolean

    @Query("SELECT * from emergency_info WHERE type = :emergencyType ")
    fun getSavedEmergencyItem(emergencyType: String): List<EmergencyInfo>
}