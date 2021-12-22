package com.timife.a911.di.modules

import android.content.Context
import android.location.Geocoder
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.timife.a911.createProfile.ui.AuthRepository
import com.timife.a911.createProfile.ui.AuthRepositoryImpl
import com.timife.a911.data.source.local.EmergencyDao
import com.timife.a911.data.source.local.EmergencyDatabase
import com.timife.a911.utils.LocationLiveData
import dagger.Module
import dagger.Provides
import java.util.*
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideLocationLiveData(context: Context): LocationLiveData {
        return LocationLiveData(context)
    }

    @Singleton
    @Provides
    fun provideGeoCoder(context: Context)= Geocoder(context, Locale.getDefault())

    @Singleton
    @Provides
    fun provideDatabase(context: Context):
            EmergencyDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            EmergencyDatabase::class.java,
            "EmergencyInfo.db"
        ).build()
    }



    @Singleton
    @Provides
    fun provideEmergencyDao(emergencyDatabase: EmergencyDatabase): EmergencyDao {
        return emergencyDatabase.emergencyDao
    }

    @Singleton
    @Provides
    fun provideAuthRepo(
        firebaseAuth: FirebaseAuth,
        firebaseDatabase: FirebaseFirestore
    ): AuthRepository = AuthRepositoryImpl(
        firebaseAuth, firebaseDatabase
    )

    @Singleton
    @Provides
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun provideFirebaseDatabase() = Firebase.firestore


}