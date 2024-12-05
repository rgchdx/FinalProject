package com.ait.finalproject.data

import com.google.android.gms.maps.model.LatLng

data class Post(
//    var userId: String = "",
    var title: String = "",
    var description: String = "",
    val contentUrl: String = "",
    val location: LatLng = LatLng(47.497913, 19.0402)
)

data class PostWithId(
    var postId: String = "",
    var post: Post
)