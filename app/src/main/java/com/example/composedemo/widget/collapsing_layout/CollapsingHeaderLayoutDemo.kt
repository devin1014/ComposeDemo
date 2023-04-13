package com.example.composedemo.widget.collapsing_layout

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CollapsingHeaderDemo() {
    CollapsingHeaderLayout(
        navigationIcon = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Filled.ArrowBack,
                    contentDescription = "ArrowBack",
                    tint = Color.White
                )
            }
        },
        title = {
            Text(
                text = "title",
                color = Color.White,
                modifier = Modifier.padding(start = 8.dp),
                fontSize = 20.sp
            )
        },
        header = {
            Box(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .background(Color.Black)
            )
        },
        contentSticker = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(color = Color.Cyan)
            ) {
                Text(text = "Sticker", modifier = Modifier.align(Alignment.Center))
            }
        },
        lazyColumnContent = {
            items(100) { index ->
                Text(
                    "I'm item $index",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        },
        scrollableContent = {
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                val modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                Box(
                    modifier = modifier
                        .background(Color.Red)
                        .border(width = 1.dp, color = Color.White, shape = RectangleShape)
                )
                Box(
                    modifier = modifier
                        .background(Color.Green)
                        .border(width = 1.dp, color = Color.White, shape = RectangleShape)
                )
                Box(
                    modifier = modifier
                        .background(Color.Blue)
                        .border(width = 1.dp, color = Color.White, shape = RectangleShape)
                )
            }
        }
    )
}
