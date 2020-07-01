package com.moon.ui.auth

import androidx.annotation.NonNull
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import retrofit2.Response

class AuthResponse<T>(
    var authStatus: AuthStatus,
    var data: T? = null,
    var message: String? = null
) {
    companion object {
        fun <T> authenticated(date: T? = null): AuthResponse<T> {
            return AuthResponse(AuthStatus.AUTHENTICATED, date, null)
        }

        fun <T> error(date: T? = null, message: String?): AuthResponse<T> {
            return AuthResponse(AuthStatus.ERROR, date, message)
        }


        fun <T> loading(date: T? = null): AuthResponse<T> {
            return AuthResponse(AuthStatus.LOADING, date, null)
        }


        fun <T> logout(date: T? = null): AuthResponse<T> {
            return AuthResponse(AuthStatus.NOT_AUTHENTICATED, null, null)
        }

    }

    public enum class AuthStatus { AUTHENTICATED, ERROR, LOADING, NOT_AUTHENTICATED }
}