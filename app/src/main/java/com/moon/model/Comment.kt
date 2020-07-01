package com.moon.model

class Comment(
    var content: String,
    var story: Story,
    var user: Account,

    publish: String = "",
    created: String = "",
    updated: String = "",
    deleted: String = ""
) : BaseModel(
    publish,
    created,
    updated,
    deleted
){
}