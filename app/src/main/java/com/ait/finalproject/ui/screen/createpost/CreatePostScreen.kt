package com.ait.finalproject.ui.screen.createpost

import android.media.MediaMetadataRetriever
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ait.finalproject.R


@Composable
fun CreatePostScreen() {
    var userId by remember {mutableStateOf("")}
    var title by remember {mutableStateOf("")}
    var description by remember {mutableStateOf("")}
    val context = LocalContext.current
    var hasPostContent by remember {mutableStateOf(false)}
    var selectedContentUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var isImage = remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedContentUri = uri
        if(uri!=null){
            val mimeType = context.contentResolver.getType(uri)
            hasPostContent=true
            if(mimeType?.startsWith("image/") == true){
                isImage.value = true
            }else if(mimeType?.startsWith("video/") == true){
                isImage.value = false
            }
        }
    }

    //Should I do image url or video? We are uploading from our own device
    /*
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            hasImage = success
        }
    )
     */
    Column(
        modifier = Modifier.padding(20.dp)
    ){
        /*
        val videoUri = MediaMetadataRetriever().run{
            setDataSource(videoUrl,HashMap())
            getFrameAtTime(0)
        }
         */
        OutlinedTextField(value = title,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(R.string.post_title)) },
            onValueChange = {
                title = it
            }
        )
        OutlinedTextField(value = description,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(text = stringResource(R.string.post_description)) },
            onValueChange = {
                description = it
            }
        )
        if(hasPostContent){
            AsyncImage(
                model = selectedContentUri,
                contentDescription = null,
                modifier = Modifier
                    .padding(4.dp)
                    .weight(1.0f)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        }
        Button(
            onClick = {
                hasPostContent = true
                //after final, create video choice points. Will need exoplayer
                launcher.launch("image/*")
            }){
            Text(text = stringResource(R.string.select_image))
        }
        Button(
            onClick = {
                UploadPost()
            }){
            Text(text = stringResource(R.string.upload_post))
        }
        //perhaps change the Column to a lazyColumn
        //Add a select coordinates button
        //Modify upload()
    }
}

fun UploadPost(){

}