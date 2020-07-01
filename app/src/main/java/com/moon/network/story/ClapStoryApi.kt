package com.moon.network.story

import com.google.gson.annotations.Expose
import com.moon.model.LikeStory
import io.reactivex.Flowable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface ClapStoryApi {

    @POST("likeStory")
    @FormUrlEncoded
    fun likeStory(@Header("Authorization") token: String? = "",
                  @Field("storyId") storyId: String, @Field("clap") clap: Int): Flowable<LikeStory>
}