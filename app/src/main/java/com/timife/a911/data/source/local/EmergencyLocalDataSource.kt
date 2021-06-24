package com.timife.a911.data.source.local

import androidx.lifecycle.LiveData
import com.timife.a911.data.EmergencyInfo
import com.timife.a911.data.Result
import com.timife.a911.data.source.EmergencyDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class EmergencyLocalDataSource(
        private val emergencyDao: EmergencyDao,
        private val ioDispatcher: CoroutineDispatcher =
                Dispatchers.IO
) :EmergencyDataSource{
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

    override suspend fun getNonEmergencyServicesItem(): LiveData<List<EmergencyInfo>> {
        TODO("Not yet implemented")
    }

}