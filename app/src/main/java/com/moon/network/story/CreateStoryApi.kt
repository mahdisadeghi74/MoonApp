package com.moon.network.story

import com.moon.model.Story
import com.moon.model.Token
import io.reactivex.Flowable
import retrofit2.http.*

interface CreateStoryApi {
    @POST("createStory/")
    @FormUrlEncoded
    fun createStory(
        @Header("Authorization") token: String? = "",
        @Field("title") title: String? = "",
        @Field("content") content: String? = "",
        @Field("category") category: Int? = 0,
        @Field("label") label: ArrayList<String>? = null,
        @Field("branch") branch: String? = "",
        @Field("currentStory") currentStory: String? = ""
    ): Flowable<Story>
}