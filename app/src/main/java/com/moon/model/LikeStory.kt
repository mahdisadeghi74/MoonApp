package com.moon.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class LikeStory(
    var id: Int? = 0,
    var story: Story? = null,
    var clap: Int? = 0,
    var user: Account? = null

) : Parcelable
