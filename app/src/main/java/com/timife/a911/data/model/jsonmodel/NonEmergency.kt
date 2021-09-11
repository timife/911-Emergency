package com.timife.a911.data.model.jsonmodel

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NonEmergency (
    @Json(name = "place") val place: String?,

//    @Json(name = "numbers") val numbers: Map<Any,Any>
    ):Parcelable