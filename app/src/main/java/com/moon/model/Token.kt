package com.moon.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Token {

    @SerializedName("token")
    @Expose
    var token: String = ""

    var message: String? = null
}