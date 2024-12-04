package com.ait.finalproject.ui.screen.discover

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.Player
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Size
import com.ait.finalproject.data.Post

@Composable
fun DiscoverScreen(
    modifier: Modifier = Modifier,
    //add viewmodel for later use
) {
    //get these values from the createpost screen
    val stringList = listOf("Title 1", "Title 2")
    val descrptionList = listOf("Description 1", "Description 2")
    val photoList = listOf("https://www.ait-budapest.com/sites/ait/files/styles/portrait/public/media/images/ekler-peter.jpg",
        "https://www.ait-budapest.com/sites/ait/files/styles/portrait/public/media/images/vizermate.jpg")
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black), //possibly change this to dark green (theme color for app)
        contentAlignment = Alignment.Center,

    ){
         PostSetup(
             videoUrls = photoList,
             titles = stringList,
             descriptions = descrptionList
         )
    }
}


//@OptIn(ExperimentalPagerApi::class)
@Composable
fun PostSetup(videoUrls: List<String>, titles: List<String>, descriptions: List<String>) {
    val pagerState = rememberPagerState(pageCount={videoUrls.size})

    VerticalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        Log.d("test", "Current page is: $page")
        Log.d("uritest","Current video is: ${videoUrls[page]}")
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            //for testing cases
            val thumbnailUri = getVideoThumbNailUri(sampleVideoUri)

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).
                data(videoUrls[page]).crossfade(true).build(),
                contentDescription = "Video Thumbnail",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                BasicText(
                    text = titles[page], //change this to username and user icon if possible
                    style = androidx.compose.ui.text.TextStyle(
                        fontSize = 20.sp,
                        color = Color.White,
                        textAlign = TextAlign.Start
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                BasicText(
                    text = descriptions[page],
                    style = androidx.compose.ui.text.TextStyle(
                        fontSize = 14.sp,
                        color = Color.LightGray,
                        textAlign = TextAlign.Start
                    )
                )
            }
        }
    }
}

/*
@Composable
fun PostCard(
    player: Player?,
    post: Post
    //addviewmodel here
){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        var currPlayer = remember {mutableStateOf(false)}

    }
}
 */

//for testing
val sampleVideoUri = "content://media/external/video/media/123"
fun getVideoThumbNailUri(videoUri: String): String{
    return "https://example.com/placeholder_thumbnail.jpg"
}