package com.timife.a911.emergencyHome.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timife.a911.data.model.jsonmodel.Emergency
import com.timife.a911.data.model.jsonmodel.NonEmergency
import com.timife.a911.data.repository.EmergencyRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val repository: EmergencyRepository) : ViewModel() {
    private val _emergency = MutableLiveData<List<Emergency>>()
    private val _nonEmergency = MutableLiveData<List<NonEmergency>>()

    init {
        getEmergencyNumbers()
    }

    private fun getEmergencyNumbers() {
        viewModelScope.launch {
            _emergency.value = repository.getEmergencyNumbers()
            _nonEmergency.value = repository.getNonEmergencyNumbers()
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


}