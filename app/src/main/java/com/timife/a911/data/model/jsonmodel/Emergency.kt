package com.timife.a911.data.model.jsonmodel

import android.os.Parcelable
import com.squareup.moshi.Json

@kotlinx.parcelize.Parcelize
data class Emergency(
    @Json(name = "Country") val country: String?,
    @Json(name = "Ambulance") val ambulance: String?,
    @Json(name = "Fire") val fire:String?,
    @Json(name = "Police") val police:String?
): Parcelable