package com.example.composedemo.widget.scroll.gusture

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.composedemo.Logging
import com.example.composedemo.resources.getItemColor
import com.example.composedemo.widget.scroll.ListItem

@Composable
fun GestureScrollDemo() {
    val scrollOffsetX = remember { mutableStateOf(0f) }
    val scrollOffsetY = remember { mutableStateOf(0f) }
    Box(modifier = Modifier
        .fillMaxSize()
        .pointerInput(Unit) {
            detectDragGestures(
                onDragStart = {
                    scrollOffsetX.value = 0f
                    scrollOffsetY.value = 0f
                }, onDragEnd = {},
                onDragCancel = {},
                onDrag = { change, dragAmount ->
                    change.consume()
                    Logging.i("onDrag: $dragAmount")
                    scrollOffsetX.value = -dragAmount.x
                    scrollOffsetY.value = -dragAmount.y
                })
        }) {
        val verticalScrollState = rememberLazyListState()
        val horizontalScrollState = rememberLazyListState()
//        val horizontalScrollStates = remember { mutableStateMapOf<Int, LazyListState>() }
//        (0..30).forEach {
//            horizontalScrollStates[it] = rememberLazyListState()
//        }
        LaunchedEffect(key1 = scrollOffsetX, block = {
            snapshotFlow { scrollOffsetX.value }.collect { offset ->
                horizontalScrollState.scrollBy(offset)
//                horizontalScrollStates.values.forEach { state ->
//                    state.scrollBy(offset)
//                }
            }
        })
        LaunchedEffect(key1 = scrollOffsetY, block = {
            snapshotFlow { scrollOffsetY.value }.collect { offset ->
                verticalScrollState.scrollBy(offset)
            }
        })
        LazyColumn(
            state = verticalScrollState,
            userScrollEnabled = false,
            content = {
                items(30) { row ->
                    if (row < 5) {
                        LazyRow(
                            state = horizontalScrollState,
                            userScrollEnabled = false,
                            content = {
                                items(30) { column ->
                                    ListItem(
                                        modifier = Modifier
                                            .size(60.dp)
                                            .background(color = getItemColor(column)),
                                        data = column.toString()
                                    )
                                }
                            })
                    } else {
                        ListItem(modifier = Modifier.size(60.dp), data = row.toString())
                    }
                }
            })
    }
}