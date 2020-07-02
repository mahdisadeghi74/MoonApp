package com.moon.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
class Category (var id: Int, val name: String = ""): Parcelable{
    companion object {
        @IgnoredOnParcel
        const val STORY_ID: Int = 1

        @IgnoredOnParcel
        const val POEM_ID: Int = 2

        @IgnoredOnParcel
        const val TRANSLATE_ID: Int = 3
    }
}