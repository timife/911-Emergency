package com.timife.a911.data.model.databasemodel

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
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
): Parcelable

