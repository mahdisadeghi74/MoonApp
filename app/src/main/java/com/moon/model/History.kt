package com.moon.model

class History (
    var story: Story,
    var currentStory: Story,
    var type: Type,
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