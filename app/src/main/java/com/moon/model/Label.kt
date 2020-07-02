package com.moon.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Label(val name: String = "") : Parcelable{

    override fun toString(): String {
        return this.name
    }
}