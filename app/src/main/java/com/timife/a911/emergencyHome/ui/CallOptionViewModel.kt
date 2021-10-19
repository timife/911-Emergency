package com.timife.a911.emergencyHome.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.timife.a911.data.model.databasemodel.EmergencyInfo
import com.timife.a911.data.repository.EmergencyRepository
import javax.inject.Inject

class CallOptionViewModel @Inject constructor(
    repository: EmergencyRepository
) : ViewModel() {

    private val _selectedNumber = MutableLiveData<EmergencyInfo>()
    val selectedNumber: LiveData<EmergencyInfo>
        get() = _selectedNumber

//    init {
//        _selectedNumber.value = emergency
//    }

}