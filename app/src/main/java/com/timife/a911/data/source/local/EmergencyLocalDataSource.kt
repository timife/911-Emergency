package com.timife.a911.data.source.local

import android.content.Context
import com.timife.a911.R
import com.timife.a911.Utils
import com.timife.a911.data.Result
import com.timife.a911.data.model.databasemodel.EmergencyInfo
import com.timife.a911.data.model.jsonmodel.Emergency
import com.timife.a911.data.model.jsonmodel.NonEmergency
import com.timife.a911.data.source.EmergencyDataSource
import com.timife.a911.di.qualifiers.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

class EmergencyLocalDataSource @Inject constructor(
        private val emergencyDao: EmergencyDao,
        @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
        private val context: Context
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

    override suspend fun getEmergencyNumbers(): ArrayList<Emergency> {
        val json = Utils.getJsonDataFromAsset(context, R.raw.emergency_numbers)
        val jsonObject = JSONObject(json!!)
        val emergencyNumbers: JSONArray = jsonObject.getJSONArray("emergency_numbers")
        val allEmergencyNumbers = ArrayList<Emergency>()
        for (i in 0 until emergencyNumbers.length()) {
            val item = emergencyNumbers.getJSONObject(i)
            val singleEmergencyNo =
                Emergency(
                    item.getJSONObject("Country").getString("Name"),
                    item.getJSONObject("Ambulance").getJSONArray("All").get(0)?.toString(),
                    item.getJSONObject("Fire").getJSONArray("All").get(0)?.toString(),
                    item.getJSONObject("Police").getJSONArray("All").get(0)?.toString()
                )
            allEmergencyNumbers.add(singleEmergencyNo)
        }
        return allEmergencyNumbers
    }


    override suspend fun getNonEmergencyNumbers(): ArrayList<NonEmergency> {
        val json = Utils.getJsonDataFromAsset(context, R.raw.non_emergency_numbers)
        val jsonObject = JSONObject(json!!)
        val nonEmergencyNumbers: JSONArray = jsonObject.getJSONArray("non_emergency_numbers")
        val nonEmergencyList = ArrayList<NonEmergency>()
        for (i in 0 until nonEmergencyNumbers.length()) {
            val item = nonEmergencyNumbers.getJSONObject(i)
            val numbersArray = item.getJSONArray("numbers")
            val numbers = mutableMapOf<Any, Any>()
            for (j in 0 until numbersArray.length()) {
                val numberItem = numbersArray.getJSONObject(j)
                val key = numberItem.keys().next()
                numbers[key] = numberItem.getString(key)
            }
            val singleNonEmergencyNo = NonEmergency(item.getString("place"), numbers)
            nonEmergencyList.add(singleNonEmergencyNo)
        }
        return nonEmergencyList
    }
}