package com.ait.finalproject.ui.screen.discover

import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.Player
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Size
import com.ait.finalproject.data.Post
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView


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
    val videoList = listOf("https://cdn.pixabay.com/video/2024/10/06/234930_large.mp4",
        "https://cdn.pixabay.com/video/2024/02/09/199958-911694865_large.mp4")
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black), //possibly change this to dark green (theme color for app)
        contentAlignment = Alignment.Center,

    ){
         PostSetup(
             videoUrls = videoList,
             titles = stringList,
             descriptions = descrptionList
         )
    }
}


//@OptIn(ExperimentalPagerApi::class)
@OptIn(UnstableApi::class)
@Composable
fun PostSetup(videoUrls: List<String>, titles: List<String>, descriptions: List<String>) {
    val pagerState = rememberPagerState(pageCount={videoUrls.size})
    val context = LocalContext.current
    val exoPlayer = remember { ExoPlayer.Builder(context).build()}
    var playerState by remember {mutableStateOf<Int?> (null)}
    var isBuffering by remember {mutableStateOf(false)}
    DisposableEffect(exoPlayer) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                playerState = playbackState
                isBuffering = when (playbackState) {
                    Player.STATE_BUFFERING -> true
                    Player.STATE_READY -> false
                    else -> isBuffering
                }
            }
        }
        exoPlayer.addListener(listener)
        onDispose {
            exoPlayer.removeListener(listener)
            exoPlayer.release()
        }
    }

    VerticalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        Log.d("test", "Current page is: $page")
        Log.d("uritest","Current video is: ${videoUrls[page]}")
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            //for testing cases
            val thumbnailUri = getVideoThumbNailUri(sampleVideoUri)

            //EXOPLAYER VIDEO ATTEMPT FROM HERE
            AndroidView(
                factory = { context ->
                    PlayerView(context).apply {
                        layoutParams = FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT
                        player = exoPlayer
                        useController = false
                    }
                },
                modifier = Modifier.fillMaxSize()
                    .clickable{
                        if(exoPlayer.isPlaying){
                            exoPlayer.pause()
                            //add pause and unpause effect later
                        }else{
                            exoPlayer.play()
                        }
                    },
                update = {
                    it.player = exoPlayer
                }
            )
            LaunchedEffect(videoUrls) {
                val mediaItem = MediaItem.fromUri(videoUrls[page])
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.prepare()
                exoPlayer.playWhenReady = true
            }
            if (isBuffering) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f), shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            }



            //EXOPLAYER VIDEO ATTEMPT END HERE

            //If cannot figure out ExoPlayer, then use this below
            /*
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).
                data(videoUrls[page]).crossfade(true).build(),
                contentDescription = "Video Thumbnail",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
             */
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