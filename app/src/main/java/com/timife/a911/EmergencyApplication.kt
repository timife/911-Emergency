package com.timife.a911

import android.app.Application
import com.timife.a911.di.DaggerEmergencyComponent
import com.timife.a911.di.EmergencyComponent

open class EmergencyApplication: Application() {
//    Instance of EmergencyComponent that'll be used by all the activities in the application
    val emergencyComponent:EmergencyComponent by lazy {
        initializeComponent()
    }

    open fun initializeComponent(): EmergencyComponent {
        return DaggerEmergencyComponent.factory().create(applicationContext)
    }
}