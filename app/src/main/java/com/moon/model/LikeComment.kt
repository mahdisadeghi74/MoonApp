package com.moon.model

class LikeComment(
    var comment: Comment,
    clap: Int,
    user: Account,

    publish: String = "",
    created: String = "",
    updated: String = "",
    deleted: String = ""
) : Like(clap, user, publish, created, updated, deleted) {
}