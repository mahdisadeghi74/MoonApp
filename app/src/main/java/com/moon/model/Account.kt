package com.moon.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
open class Account(
    var email: String? = "",
    var pk: String? = "",
    var message: String? = "",
    var firstName: String? = "",
    var lastName: String? = "",
    var username: String? = "",
    var number: String? = "",
    var date_joined: String? = "",
    var last_login: String? = "",
    var is_admin: Boolean? = false,
    var is_active: Boolean? = false,
    var is_staff: Boolean? = false,
    var is_superuser: Boolean? = false,
    var publish: String = "",
    var created: String = "",
    var updated: String = "",
    var deleted: String = ""
) : Parcelable
