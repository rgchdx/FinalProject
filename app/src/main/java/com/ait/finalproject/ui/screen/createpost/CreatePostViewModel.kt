package com.ait.finalproject.ui.screen.createpost
//
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.lifecycle.ViewModel
//import com.ait.finalproject.data.Post
//import com.google.firebase.firestore.FirebaseFirestore
//
//class CreatePostViewModel: ViewModel() {
//    var createPostUiState: CreatePostUiState by mutableStateOf(CreatePostUiState.init)
//    fun upload(
//        title: String,
//        description: String,
//        content: String
//    ){
//        createPostUiState = CreatePostUiState.LoadingPostUpload
//        val newPost = Post(
//            //change userId to auth later on after incorporation of authentication
////            userId = "test",
//            title = title,
//            description = description,
//            contentUrl = content
//        )
//        val postsCollection = FirebaseFirestore.getInstance().collection(
//            "posts") //creating an empty collection(table)
//        postsCollection.add(newPost)
//            .addOnSuccessListener {
//                createPostUiState = CreatePostUiState.PostUploadSuccess
//            }
//            .addOnFailureListener {
//                createPostUiState = CreatePostUiState.ErrorDuringPostUpload("Upload Failed")
//                //change the "Upload Failed" to it.messages later on after incorporation of authentication
//            }
//    }
//}
//
//sealed interface CreatePostUiState {
//    object init: CreatePostUiState
//    object LoadingPostUpload: CreatePostUiState
//    object PostUploadSuccess : CreatePostUiState
//    data class ErrorDuringPostUpload(val error: String?) : CreatePostUiState
//
//    object LoadingContentUpload : CreatePostUiState
//    data class ErrorDuringContentUpload(val error: String?) : CreatePostUiState
//    object ContentUploadSuccess : CreatePostUiState
//}