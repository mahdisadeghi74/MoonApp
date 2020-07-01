package com.moon.model

open class Like(
    var clap: Int,
    var user: Account,

    publish: String = "",
    created: String = "",
    updated: String = "",
    deleted: String = ""
) : BaseModel(
    publish,
    created,
    updated,
    deleted){
}