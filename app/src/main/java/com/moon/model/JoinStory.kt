package com.moon.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class JoinStory(
    @Expose @SerializedName("id") var id: String ?= "",
    @Expose @SerializedName("userName") var userName: String ?= "",
    @Expose @SerializedName("user") var userId: String ?= "",
    @Expose @SerializedName("isActive") var isActive: Boolean = false,
    @Expose @SerializedName("isAccepted") var isAccepted: Boolean = false
) {
}