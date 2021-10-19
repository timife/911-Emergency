package com.timife.a911.emergencyHome.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadilq.liveevent.LiveEvent
import com.timife.a911.data.model.databasemodel.EmergencyInfo
import com.timife.a911.data.model.jsonmodel.Emergency
import com.timife.a911.data.model.jsonmodel.NonEmergency
import com.timife.a911.data.repository.EmergencyRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class EmergencyStatus { LOADING, ERROR, DONE }

class HomeViewModel @Inject constructor(private val repository: EmergencyRepository) : ViewModel() {
    private val _emergency = MutableLiveData<List<Emergency>>()
    private val _nonEmergency = MutableLiveData<List<NonEmergency>>()

    private val _navigateToSaveOption = LiveEvent<EmergencyInfo>()
    val navigateToSaveOption: LiveEvent<EmergencyInfo>
        get() = _navigateToSaveOption

    private val _status = MutableLiveData<EmergencyStatus>()
    val status: LiveData<EmergencyStatus>
        get() = _status


    init {
        getEmergencyNumbers()
    }

    private fun getEmergencyNumbers() {
        viewModelScope.launch {
            try {
                _emergency.value = repository.getEmergencyNumbers()
                _nonEmergency.value = repository.getNonEmergencyNumbers()
            } catch (e: Exception) {
                _emergency.value = ArrayList()
            }


        }
    }

    fun searchEmergencyNumbers(country: String): LiveData<Emergency> {
        val emergencyNumbers = MutableLiveData<Emergency>()
        viewModelScope.launch {
            val emergencyNumbersList = _emergency.value?.filter {
                it.country.equals(country, ignoreCase = true)
            }
            if (emergencyNumbersList != null) {
                if (emergencyNumbersList.isNotEmpty()) {
                    emergencyNumbers.value = emergencyNumbersList[0]
                }
            }
        }

        return emergencyNumbers

    }


    fun searchNonEmergencyNumbers(state: String): LiveData<NonEmergency> {
        val nonEmergencyNumbers = MutableLiveData<NonEmergency>()
        viewModelScope.launch {
            val nonEmergencyNumbersList = _nonEmergency.value?.filter {
                it.place.equals(state, ignoreCase = true)
            }
            if (nonEmergencyNumbersList != null) {
                if (nonEmergencyNumbersList.isNotEmpty()) {
                    nonEmergencyNumbers.value = nonEmergencyNumbersList[0]
                }
            }
        }
        return nonEmergencyNumbers
    }

    fun passEmergencyDetails(number: EmergencyInfo) {
        _navigateToSaveOption.value = number
    }
//    fun passEmergencyDetailsComplete(){
//        _navigateToSaveOption.value =
//    }


}