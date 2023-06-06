package com.example.composedemo.widget.scroll

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composedemo.Logging
import com.example.composedemo.widget.toPix

@Composable
fun LazyColumnScrollDemo() {
    val itemHeight = 100.dp.toPix()
    Box(modifier = Modifier.fillMaxSize()) {
        val scrollOffset = remember { mutableStateOf(0) }
        val scrollState = rememberLazyListState(0, 0)
        LaunchedEffect(key1 = scrollState, block = {
//            snapshotFlow { scrollState.firstVisibleItemIndex }.collect {
//                Logging.d("----------------------------")
//                Logging.d("index: $it")
//                Logging.d("----------------------------")
//            }
//            snapshotFlow { scrollState.firstVisibleItemScrollOffset }.collect {
//                Logging.d("offset: $it")
//            }
            snapshotFlow { (scrollState.firstVisibleItemIndex * itemHeight) + scrollState.firstVisibleItemScrollOffset }.collect {
                Logging.d("offset: $it")
                scrollOffset.value = it
            }
        })
        LazyColumn(
            state = scrollState,
            content = {
                items(30) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .padding(horizontal = 6.dp)
                            .clickable { },
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = (it + 1).toString(),
                            fontSize = 14.sp
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(color = Color.Magenta)
                                .align(Alignment.BottomStart)
                        )
                    }
                }
            })
        Text(
            text = "itemHeight: $itemHeight\noffset: ${scrollOffset.value}",
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}