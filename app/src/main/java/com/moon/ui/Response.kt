package com.moon.ui

import com.moon.model.Account
import com.moon.ui.auth.AuthResponse

class Response<T>(
    var status: Status,
    var data: T?=null,
    var message: String? = null
) {

    companion object{
        fun <T> success(data: T?): Response<T>{
            return Response(Status.SUCCESS, data, null)
        }

        fun <T> loading(data: T? = null): Response<T>{
            return Response(Status.LOADING, data, null)
        }

        fun <T> error(data: T? = null, message: String? = null): Response<T>{
            return Response(Status.ERROR, data, message)
        }
    }
    enum class Status { SUCCESS, ERROR, LOADING }
}