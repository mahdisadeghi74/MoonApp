package com.moon.network.label

import com.moon.model.Label
import com.moon.model.Token
import io.reactivex.Flowable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface GetLabelsApi {
    @POST("getLabels/")
    @FormUrlEncoded
    fun getLabels(@Header("Authorization") token: String? ="", @Field("search") search: String): Flowable<ArrayList<Label>>
}