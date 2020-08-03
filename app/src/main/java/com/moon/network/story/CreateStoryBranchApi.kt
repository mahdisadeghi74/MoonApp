package com.moon.network.story

import com.moon.model.Story
import com.moon.model.Token
import io.reactivex.Flowable
import retrofit2.http.*

interface CreateStoryBranchApi {
    @POST("createStoryBranch/")
    @FormUrlEncoded
    fun createStoryBranch(
        @Header("Authorization") token: String? = "",
        @Field("storyId") storyId: String? = "",
        @Field("branch") branch: String? = ""
    ): Flowable<Story>
}