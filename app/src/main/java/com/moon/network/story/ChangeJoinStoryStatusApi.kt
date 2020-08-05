package com.moon.network.story

import com.moon.model.JoinStory
import io.reactivex.Flowable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface ChangeJoinStoryStatusApi {
    @POST("updateJoinUser/")
    @FormUrlEncoded
    fun joinStory(
        @Header("Authorization") token: String? = "",
        @Field("storyId") storyId: String? = "",
        @Field("id") id: String? = "",
        @Field("isActive") isActive: Boolean? = false,
        @Field("isAccepted") isAccepted: Boolean? = false,
        @Field("memberId") memberId: String? = ""
    ): Flowable<JoinStory>
}