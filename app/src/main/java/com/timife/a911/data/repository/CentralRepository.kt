package com.timife.a911.data.repository

import com.timife.a911.data.EmergencyInfo
import com.timife.a911.data.Result
import com.timife.a911.data.source.local.EmergencyLocalDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class CentralRepository @Inject constructor(
    private val emergencyLocalDataSource: EmergencyLocalDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : EmergencyRepository {
    override suspend fun upsert(item: EmergencyInfo) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(item: EmergencyInfo) {
        TODO("Not yet implemented")
    }

    override suspend fun checkIfExist(item: EmergencyInfo): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getEmergencyServicesItem(): Result<List<EmergencyInfo>> {
        TODO("Not yet implemented")
    }

    override suspend fun getNonEmergencyServicesItem(): Result<List<EmergencyInfo>> {
        TODO("Not yet implemented")
    }

}