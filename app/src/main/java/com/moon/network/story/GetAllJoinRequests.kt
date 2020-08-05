package com.moon.network.story

import com.moon.model.JoinStory
import com.moon.model.Story
import com.moon.model.StringResponse
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface GetAllJoinRequests {
    @POST("getAllJoinStoriesRequest/")
    @FormUrlEncoded
    fun joinStory(
        @Header("Authorization") token: String? = "",
        @Field("storyId") storyId: String? = ""
    ): Flowable<ArrayList<JoinStory>>
}