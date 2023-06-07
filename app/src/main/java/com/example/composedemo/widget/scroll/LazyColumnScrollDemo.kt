package com.example.composedemo.widget.scroll

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composedemo.Logging
import com.example.composedemo.widget.toPix

private val lists = (1..30).toList()
private val colors = listOf(Color.Cyan, Color.Blue, Color.Green, Color.DarkGray, Color.Magenta)

@Composable
fun LazyColumnScrollDemo() {
    val itemHeight = 100.dp.toPix()
    Box(modifier = Modifier.fillMaxSize()) {
        val scrollOffset = remember { mutableStateOf(0) }
        Row {
            val primaryScrollState = rememberLazyListState()
            val secondScrollState = rememberLazyListState()
            LaunchedEffect(key1 = primaryScrollState, block = {
                snapshotFlow { (primaryScrollState.firstVisibleItemIndex * itemHeight) + primaryScrollState.firstVisibleItemScrollOffset }.collect {
                    Logging.d("offset: $it")
                    scrollOffset.value = it
                    secondScrollState.scrollToItem(primaryScrollState.firstVisibleItemIndex, primaryScrollState.firstVisibleItemScrollOffset)
                }
            })
            // primary
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .fillMaxHeight(),
                state = primaryScrollState,
                content = {
                    items(lists) {
                        ListItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .background(color = colors[it % colors.size]), index = it
                        )
                    }
                })
            // second
            LazyColumn(
                modifier = Modifier,
                state = secondScrollState,
                content = {
                    items(lists) {
                        ListItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .background(color = colors[it % colors.size]), index = it
                        )
                    }
                })
        }
        Text(
            text = "offset: ${scrollOffset.value}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun ListItem(modifier: Modifier, index: Int) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = index.toString(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}