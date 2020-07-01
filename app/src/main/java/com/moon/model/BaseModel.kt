package com.moon.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
open class BaseModel(
    var publish: String = "",
    var created: String = "",
    var updated: String = "",
    var deleted: String = ""
) : Parcelable{
}