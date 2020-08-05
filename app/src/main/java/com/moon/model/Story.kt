package com.moon.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.io.Serializable

@Parcelize
class Story(
    @SerializedName("title") @Expose var title: String? = "",
    @SerializedName("content") @Expose var content: String = "",
    @SerializedName("category") @Expose var category: Category? = null,
    @SerializedName("label") @Expose var label: ArrayList<Label> = arrayListOf(),
    @SerializedName("branch") @Expose var branch: String = "",
    @SerializedName("currentStory") @Expose var currentStory: Int? = null,
    @SerializedName("created_by") var created_by: String? = "",
    @SerializedName("parent") @Expose var parent: String? = "",
    @SerializedName("id") @Expose var id: String? = "",
    @SerializedName("clapCount") @Expose var clapCount: Int? = 0,

    var publish: String = "",
    var created: String = "",
    var updated: String = "",
    var deleted: String = ""
) : Parcelable {

}