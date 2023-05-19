package com.example.composedemo.widget.scroll

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composedemo.Logging

val columns = (1..100).toList()
val colors = listOf(Color.Cyan, Color.Blue, Color.Green, Color.DarkGray, Color.Magenta)

@Composable
fun ScrollDemo() {
    Row {
        val firstScrollState = rememberLazyListState()
        val secondScrollState = rememberLazyListState()
        if (firstScrollState.isScrollInProgress) {
            DisposableEffect(Unit) {
//                firstScrollState.scrollToItem()
                Logging.info("firstVisibleItemScrollOffset: ${firstScrollState.firstVisibleItemScrollOffset}")
                onDispose {

                }
            }
        }
        LazyColumn(
            modifier = Modifier
                .width(120.dp)
                .fillMaxHeight(),
            state = firstScrollState,
            content = {
                items(columns) {
                    Box(
                        modifier = Modifier
                            .width(120.dp)
                            .height(30.dp)
                            .background(color = colors[it % colors.size]),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = it.toString(), fontSize = 16.sp)
                    }
                }
            }
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = secondScrollState,
            content = {
                items(columns) {
                    Box(
                        modifier = Modifier
                            .width(240.dp)
                            .height(30.dp)
                            .background(color = colors[(it + 1) % colors.size]),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = it.toString(), fontSize = 16.sp)
                    }
                }
            }
        )
    }
}

@Preview
@Composable
fun ScrollDemoPreview() {
    ScrollDemo()
}
