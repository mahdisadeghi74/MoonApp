package com.moon.network.auth

import com.moon.model.Token
import com.moon.ui.auth.AuthResponse
import io.reactivex.Flowable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded

import retrofit2.http.POST

interface AuthApi {

    @FormUrlEncoded
    @POST("login/")
    fun authentication(@Field("username") username: String
                       ,@Field("password") password: String): Flowable<Token>
}