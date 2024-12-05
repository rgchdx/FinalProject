package com.ait.finalproject.ui.screen.discover

import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import androidx.navigation.NavHostController
import com.ait.finalproject.R
import com.ait.finalproject.data.Post
import com.ait.finalproject.ui.navigation.MainNavigation
import com.google.android.gms.maps.model.LatLng


@Composable
fun DiscoverScreen(
    modifier: Modifier = Modifier,
    navigation: NavHostController
) {
    // get these values from the createpost screen
//    val stringList = listOf("Title 1", "Title 2")
//    val descrptionList = listOf("Description 1", "Description 2")
//    val photoList = listOf("https://www.ait-budapest.com/sites/ait/files/styles/portrait/public/media/images/ekler-peter.jpg",
//        "https://www.ait-budapest.com/sites/ait/files/styles/portrait/public/media/images/vizermate.jpg")
//    val videoList = listOf("https://cdn.pixabay.com/video/2024/10/06/234930_large.mp4",
//        "https://cdn.pixabay.com/video/2024/02/09/199958-911694865_large.mp4")

    val postList = listOf(
        Post(
            title = stringResource(R.string.beautiful_hike_in_aquincum),
            description = stringResource(R.string.you_should_go_on_a_hike_in_the_morning_location_aquincum_mountain_idk),
            contentUrl = stringResource(R.string.https_cdn_pixabay_com_video_2024_10_06_234930_large_mp4),
            location = LatLng(47.5591, 19.0503)
        ),
        Post(
            title = stringResource(R.string.mountain_biking_at_knox_college_china),
            description = stringResource(R.string.biking_is_gas_guys_yall_should_try_it),
            contentUrl = stringResource(R.string.https_cdn_pixabay_com_video_2024_02_09_199958_911694865_large_mp4),
            location = LatLng(40.9417, 90.3721)
        )
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black), //possibly change this to dark green (theme color for app)
        contentAlignment = Alignment.Center,

    ){
         PostSetup(
             navigation = navigation,
             posts = postList
         )
    }
}


//@OptIn(ExperimentalPagerApi::class)
@OptIn(UnstableApi::class)
@Composable
fun PostSetup(
    posts: List<Post>,
    navigation: NavHostController
) {
    val pagerState = rememberPagerState(pageCount={posts.size})
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
        //Log.d("test", "Current page is: $page")
        val post = posts[page]
        //Log.d("uritest","Current video is: ${post.contentUrl}")
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
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        if (exoPlayer.isPlaying) {
                            exoPlayer.pause()
                            //add pause and unpause effect later
                        } else {
                            exoPlayer.play()
                        }
                    },
                update = {
                    it.player = exoPlayer
                }
            )
            LaunchedEffect(posts) {
                val mediaItem = MediaItem.fromUri(post.contentUrl)
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.prepare()
                exoPlayer.playWhenReady = true
            }
            if (isBuffering) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Color.Black.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(16.dp)
                        ),
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
                Row(
                    modifier = Modifier.fillMaxWidth()
                ){
                    BasicText(
                        text = post.title, //change this to username and user icon if possible
                        modifier = Modifier.weight(2.0f),
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = Color.White,
                            textAlign = TextAlign.Start
                        )
                    )
                    Button(
                        onClick = {
                            navigation.navigate(MainNavigation.MapScreen.createRoute(post.location.latitude, post.location.longitude))
                          },
                        modifier = Modifier.weight(1.0f),
                    ){
                        Text(text = stringResource(R.string.go_see_in_map))
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                BasicText(
                    text = post.description,
                    style = TextStyle(
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