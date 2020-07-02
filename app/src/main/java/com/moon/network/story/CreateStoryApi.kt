package com.moon.network.story

import com.moon.model.Token
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface CreateStoryApi {
    @POST("createStory/")
    @FormUrlEncoded
    fun createStory(
        @Header("Authorization") token: String,
        @Field("title") title: String
    )
}