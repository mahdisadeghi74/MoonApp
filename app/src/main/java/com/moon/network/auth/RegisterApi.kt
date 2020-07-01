package com.moon.network.auth

import com.moon.model.Account
import io.reactivex.Flowable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RegisterApi {

    @FormUrlEncoded
    @POST("register/")
    fun register(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("password2") password2: String,
        @Field("username") username: String
    ): Flowable<Account>
}