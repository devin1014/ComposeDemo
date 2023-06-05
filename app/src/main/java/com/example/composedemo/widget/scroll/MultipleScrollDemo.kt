package com.example.composedemo.widget.scroll

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composedemo.Logging

val columns = (1..20).toList()
val colors = listOf(Color.Cyan, Color.Blue, Color.Green, Color.DarkGray, Color.Magenta)

@Composable
fun MultipleScrollDemo() {
    Row {
        val loggingDensity = remember { mutableStateOf(false) }
        if (!loggingDensity.value) {
            with(LocalDensity) { Logging.info("density: ${this.current.density}") }
            loggingDensity.value = true
        }
        val firstScrollState = rememberLazyListState()
        val secondScrollState = rememberLazyListState()
        val totalScrollOffset = remember { mutableStateOf(0) }
        if (firstScrollState.isScrollInProgress) {
            //进入组合后只会启动一次，
            DisposableEffect(Unit) {
                onDispose {
                    Logging.d("滑动结束: ${totalScrollOffset.value}")
                }
            }
            //记录上一次第一个可见item的滑动偏移
            val lastFirstVisibleItemScrollOffset = remember { mutableStateOf(firstScrollState.firstVisibleItemScrollOffset) }
            //记录上一次第一个可见item下标
            val lastFirstVisibleItemIndex = remember { mutableStateOf(firstScrollState.firstVisibleItemIndex) }

            run {
                val currentFirstVisibleItemIndex = firstScrollState.firstVisibleItemIndex
                //手指向上滑动（即正方向）时offset会变大，向下时变小
                val currentFirstVisibleItemScrollOffset = firstScrollState.firstVisibleItemScrollOffset
                //第一个可见的item改变了
                if (currentFirstVisibleItemIndex != lastFirstVisibleItemIndex.value) {
                    totalScrollOffset.value = totalScrollOffset.value + firstScrollState.firstVisibleItemScrollOffset
                    if (currentFirstVisibleItemIndex < lastFirstVisibleItemIndex.value) {
                        //Logging.i("向下滑动↓")
                    } else if (currentFirstVisibleItemIndex > lastFirstVisibleItemIndex.value) {
                        //Logging.i("向上滑动↑")
                    }
                    //更新记录的值，退出run代码块
                    lastFirstVisibleItemIndex.value = currentFirstVisibleItemIndex
                    lastFirstVisibleItemScrollOffset.value = currentFirstVisibleItemScrollOffset
                    return@run
                }
                //第一个可见item当前的offset - 上一次记录的offset
                val offset = currentFirstVisibleItemScrollOffset - lastFirstVisibleItemScrollOffset.value
                if (offset < 0) {
                    Logging.d("向下滑动↓ $offset")
                } else if (offset > 0) {
                    Logging.d("向上滑动↑ $offset")
                }
                totalScrollOffset.value = totalScrollOffset.value + offset
                //记录第一个可见item当前的offset
                lastFirstVisibleItemScrollOffset.value = currentFirstVisibleItemScrollOffset
            }
            LaunchedEffect(key1 = totalScrollOffset.value, block = {
                secondScrollState.scrollToItem(firstScrollState.firstVisibleItemIndex, firstScrollState.firstVisibleItemScrollOffset)
            })
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
                            .height(90.dp)
                            .background(color = colors[it % colors.size]),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = it.toString(), fontSize = 20.sp)
                    }
                }
            }
        )
        val rowScrollStateArray = remember { mutableMapOf<Int, ScrollState?>() }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = secondScrollState,
            content = {
                items(columns) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(90.dp)
                            .background(color = colors[(it + 1) % colors.size]),
                        contentAlignment = Alignment.Center
                    ) {
//                        Text(text = it.toString(), fontSize = 20.sp)
//                        LazyRow(content = {
//                            items(20) { rowItem ->
//                                Text(text = rowItem.toString(), fontSize = 20.sp)
//                            }
//                        })
                        val itemRowScrollState: ScrollState = rowScrollStateArray[it] ?: rememberScrollState()
                        rowScrollStateArray[it] = itemRowScrollState
                        LaunchedEffect(key1 = itemRowScrollState) {
                            snapshotFlow { itemRowScrollState.value }.collect { scrollOffset ->
                                Logging.d("ScrollState: $scrollOffset")
                                rowScrollStateArray.values.filterNotNull().forEach { innerState ->
                                    if (innerState != itemRowScrollState) {
                                        innerState.scrollTo(scrollOffset)
                                    }
                                }
                            }
                        }
                        Row(
                            modifier = Modifier.horizontalScroll(itemRowScrollState),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            repeat(20) { rowItem ->
                                Text(
                                    modifier = Modifier.width(120.dp),
                                    text = rowItem.toString(),
                                    textAlign = TextAlign.Center,
                                    fontSize = 20.sp
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}

@Preview
@Composable
fun MultipleScrollDemoPreview() {
    MultipleScrollDemo()
}
