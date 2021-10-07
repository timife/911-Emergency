package com.timife.a911.data.repository

import com.timife.a911.data.Result
import com.timife.a911.data.model.databasemodel.EmergencyInfo
import com.timife.a911.data.model.jsonmodel.Emergency
import com.timife.a911.data.model.jsonmodel.NonEmergency
import com.timife.a911.data.source.EmergencyDataSource
import com.timife.a911.di.qualifiers.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EmergencyRepositoryImpl @Inject constructor(
    private val emergencyLocalDataSource: EmergencyDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): EmergencyRepository {
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

    override fun getEmergencyNumbers(): ArrayList<Emergency> =
        emergencyLocalDataSource.getEmergencyNumbers()


    override fun getNonEmergencyNumbers(): ArrayList<NonEmergency> =
        emergencyLocalDataSource.getNonEmergencyNumbers()


}