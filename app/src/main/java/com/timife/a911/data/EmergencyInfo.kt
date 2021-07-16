package com.timife.a911.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "emergency_info")
data class EmergencyInfo(
    @PrimaryKey(autoGenerate = false)
    var id: String,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "number")
    var phone: String,
    var type: String? = null,
    @ColumnInfo(name = "location")
    var place: String
)

data class NonEmergency(val place: String, val numbers: Map<Any, Any>)