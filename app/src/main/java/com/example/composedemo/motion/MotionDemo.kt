package com.example.composedemo.motion

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import com.example.composedemo.R
import com.example.composedemo.R.drawable

@OptIn(ExperimentalMotionApi::class)
@Composable
fun MotionDemo() {
    val context = LocalContext.current
    val motionScene = remember {
        context.resources.openRawResource(R.raw.motion_scene).readBytes().decodeToString()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {
        MotionLayout(
            motionScene = MotionScene(content = motionScene),
            progress = 0.5f
        ) {
            Image(
                painter = painterResource(id = drawable.league_pass_logo),
                contentDescription = "",
                modifier = Modifier
                    .size(150.dp)
                    .layoutId("headerImage")
            )
        }
        Image(
            painter = painterResource(id = drawable.menu_games_selected),
            contentDescription = "",
            modifier = Modifier
                .layoutId("headerImage2")
                .align(Alignment.TopCenter)
        )
    }
}