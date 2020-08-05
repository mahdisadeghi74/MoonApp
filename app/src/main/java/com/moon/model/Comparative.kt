package com.moon.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Comparative(
    @Expose @SerializedName("storyContentNew") var storyContentNew: String? = "",
    @Expose @SerializedName("storyContentOld") var storyContentOld: String? = "",
    @Expose @SerializedName("add") var addPosition: ArrayList<Int>? = null,
    @Expose @SerializedName("delete") var deletePosition: ArrayList<Int>? = null
) {

}