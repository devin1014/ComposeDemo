package com.example.composedemo.widget.collapsing_layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ScrollableAppBarDemo() {

    CollapsingHeaderLayout(
        toolbarHeight = 56.dp,
        collapsingToolbarLayoutHeight = 200.dp,
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
        background = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            )
        }
    ) { offset, max ->
        val nestedScrollConnection = remember {
            object : NestedScrollConnection {
                override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                    return Offset.Zero
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .nestedScroll(nestedScrollConnection)
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .offset { IntOffset(x = 0, y = offset) }) {
                val modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                Box(modifier = modifier)
                Box(
                    modifier
                        .height(250.dp)
                        .background(color = Color.Red)
                )
            }
            LazyColumn(
                contentPadding = PaddingValues(top = 450.dp)
            ) {
                items(100) { index ->
                    Text(
                        "I'm item $index",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }
        }

        // list
//        LazyColumn(
//            contentPadding = PaddingValues(top = 200.dp + 56.dp)
//        ) {
//            items(100) { index ->
//                Text(
//                    "I'm item $index",
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp)
//                )
//            }
//        }
//        TabRow(
//            selectedTabIndex = 0,
//            modifier = Modifier
//                .padding(top = 200.dp)
//                .height(56.dp)
//                .fillMaxWidth()
//                .offset { IntOffset(x = 0, y = offsetPx) },
//        ) {
//            repeat(4) { index ->
//                Tab(selected = index == 0, onClick = { }) {
//                    Box(
//                        modifier = Modifier.fillMaxSize(),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Text(text = index.toString())
//                    }
//                }
//            }
//        }
    }
}
