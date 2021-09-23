package com.timife.a911.data.model.jsonmodel

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

data class NonEmergency(val place: String, val numbers: Map<Any, Any>)
