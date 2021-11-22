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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

enum class EmergencyStatus { LOADING, ERROR, DONE }

class HomeViewModel @Inject constructor(private val repository: EmergencyRepository) : ViewModel() {
    private val _emergency = MutableLiveData<List<Emergency>>()
    val emergency: LiveData<List<Emergency>>
        get() = _emergency

    private val _nonEmergency = MutableLiveData<List<NonEmergency>>()
    val nonEmergency: LiveData<List<NonEmergency>>
        get() = _nonEmergency

    private val _navigateToSaveOption = LiveEvent<EmergencyInfo>()
    val navigateToSaveOption: LiveEvent<EmergencyInfo>
        get() = _navigateToSaveOption
    private val _navigateToNonSaveOption = LiveEvent<EmergencyInfo>()
    val navigateToNonSaveOption: LiveEvent<EmergencyInfo>
        get() = _navigateToNonSaveOption


    private val _status = MutableLiveData<EmergencyStatus>()
    val status: LiveData<EmergencyStatus>
        get() = _status


    init {
        getEmergencyNumbers()
    }

    private fun getEmergencyNumbers() {
        viewModelScope.launch {
            try {
                _status.value = EmergencyStatus.LOADING
                val emergency = withContext(Dispatchers.IO) {
                    repository.getEmergencyNumbers()
                }
                val nonEmergency = withContext(Dispatchers.IO) {
                    repository.getNonEmergencyNumbers()
                }

                _emergency.value = emergency
                _nonEmergency.value = nonEmergency
                _status.value = EmergencyStatus.DONE
            } catch (e: Exception) {
                _status.value = EmergencyStatus.LOADING
                _emergency.value = ArrayList()
                _nonEmergency.value = ArrayList()
            }
        }
    }

    fun passEmergencyDetails(number: EmergencyInfo) {
        _navigateToSaveOption.value = number
    }

    fun passNonEmergencyDetails(number: EmergencyInfo) {
        _navigateToNonSaveOption.value = number
    }
}