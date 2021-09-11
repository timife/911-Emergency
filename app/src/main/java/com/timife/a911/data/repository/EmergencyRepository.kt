package com.timife.a911.data.repository

import com.timife.a911.data.Result
import com.timife.a911.data.model.databasemodel.EmergencyInfo


interface EmergencyRepository {

    suspend fun upsert(item: EmergencyInfo)

    suspend fun delete(item: EmergencyInfo)

    suspend fun checkIfExist(item: EmergencyInfo): Result<Boolean>

    suspend fun getEmergencyServicesItem(): Result<List<EmergencyInfo>>

    suspend fun getNonEmergencyServicesItem(): Result<List<EmergencyInfo>>
}