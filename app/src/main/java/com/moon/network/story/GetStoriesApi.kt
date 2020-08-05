package com.moon.network.story

import com.moon.model.LikeStory
import com.moon.model.Story
import com.moon.model.Token
import io.reactivex.Flowable
import retrofit2.http.*

interface GetStoriesApi {

    @POST("getStories/")
    @FormUrlEncoded
    fun getStories(
        @Header("Authorization") token: String? = "",
        @Field("search") search: String? = "",
        @Field("parent") parentId: String? = ""
    ): Flowable<ArrayList<Story>>

    @POST("likeStory/")
    @FormUrlEncoded
    fun likeStory(@Header("Authorization") token: String? = "",
                  @Field("storyId") storyId: String, @Field("clap") clap: Int): Flowable<LikeStory>
}