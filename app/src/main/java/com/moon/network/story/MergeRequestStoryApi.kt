package com.moon.network.story

import com.moon.model.Story
import io.reactivex.Flowable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface MergeRequestStoryApi {
    @POST("mergeRequestStory/")
    @FormUrlEncoded
    fun mergeRequest(
        @Header("Authorization") token: String? = "",
        @Field("storyId") storyId: String? = "",
        @Field("storyContentNew") content: String? = ""
    ): Flowable<Story>
}