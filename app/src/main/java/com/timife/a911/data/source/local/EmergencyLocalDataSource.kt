package com.timife.a911.data.source.local

import com.timife.a911.data.Result
import com.timife.a911.data.model.databasemodel.EmergencyInfo
import com.timife.a911.data.source.EmergencyDataSource
import com.timife.a911.di.qualifiers.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class EmergencyLocalDataSource @Inject constructor(
        private val emergencyDao: EmergencyDao,
        @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : EmergencyDataSource{
    override suspend fun upsert(item: EmergencyInfo) {

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