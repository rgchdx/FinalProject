package com.ait.finalproject.data

data class Post(
    var userId: String = "",
    var title: String = "",
    var description: String = "",
    //val date: Date = Date(),
    val contentId: String = ""
)

data class PostWithId(
    var postId: String = "",
    var post: Post
)