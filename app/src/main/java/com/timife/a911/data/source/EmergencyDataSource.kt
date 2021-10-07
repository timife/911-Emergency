package com.timife.a911.data.source

import com.timife.a911.data.Result
import com.timife.a911.data.model.databasemodel.EmergencyInfo
import com.timife.a911.data.model.jsonmodel.Emergency
import com.timife.a911.data.model.jsonmodel.NonEmergency

interface EmergencyDataSource {
    suspend fun upsert(item: EmergencyInfo)

    suspend fun delete(item: EmergencyInfo)

    suspend fun checkIfExist(item: EmergencyInfo): Result<Boolean>

    suspend fun getEmergencyServicesItem(): Result<List<EmergencyInfo>>

    suspend fun getNonEmergencyServicesItem(): Result<List<EmergencyInfo>>

    fun getEmergencyNumbers():ArrayList<Emergency>

    fun getNonEmergencyNumbers():ArrayList<NonEmergency>


}
