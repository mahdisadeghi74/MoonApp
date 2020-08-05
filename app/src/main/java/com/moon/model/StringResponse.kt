package com.moon.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class StringResponse(
    @Expose @SerializedName("result") var result: String) {
}