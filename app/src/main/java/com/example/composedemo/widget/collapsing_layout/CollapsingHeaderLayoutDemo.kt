package com.example.composedemo.widget.collapsing_layout

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CollapsingHeaderDemo() {
    val collapsing = remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize()) {
        CollapsingHeaderLayout(
            toolbar = {
                Toolbar()
            },
            headerContent = {
                if (!collapsing.value) {
                    Header()
                }
            },
            contentSticker = {
                Sticker()
            },
            // lazyColumnContent、scrollableContent 互斥
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
                ScrollableContent()
            },
            onCollapsingChanged = { percent ->
                collapsing.value = percent >= 98
            }
        )
        if (collapsing.value) {
            Box(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(0.5f)
                    .align(Alignment.BottomEnd)
            ) {
                Header()
            }
        }
    }
}

@Composable
fun NavigationIcon() {
    IconButton(onClick = { }) {
        Icon(
            imageVector = Filled.ArrowBack,
            contentDescription = "ArrowBack",
            tint = Color.White
        )
    }
}

@Composable
fun Title() {
    Text(
        text = "title",
        color = Color.White,
        modifier = Modifier.padding(start = 8.dp),
        fontSize = 20.sp
    )
}

@Composable
fun Header() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(ratio = 16 / 9f)
            .background(Color.Black)
    )
}

@Composable
fun Sticker() {
    Box(
        Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(color = Color.Cyan)
    ) {
        Text(text = "Sticker", modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun ScrollableContent() {
    Box {
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
}

@Composable
fun Toolbar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(color = Color.Black),
        verticalAlignment = Alignment.CenterVertically
    ) {
        NavigationIcon()
        Spacer(modifier = Modifier.width(12.dp))
        Title()
    }
}
