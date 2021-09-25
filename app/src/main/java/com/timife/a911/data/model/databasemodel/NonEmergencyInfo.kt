package com.timife.a911.data.model.databasemodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "non_emergency_info")
data class NonEmergencyInfo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "place")
    val place: String,
    @ColumnInfo(name = "numbers")
    val numbers: Map<Any, Any>
)