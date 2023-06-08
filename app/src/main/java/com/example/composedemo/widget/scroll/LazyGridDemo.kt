package com.example.composedemo.widget.scroll

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composedemo.Logging

@Composable
fun LazyGridDemo() {
    //TODO: failed!!!
    Box(modifier = Modifier.fillMaxSize()) {
        val rowScrollFirstItemIndex = remember { mutableStateOf(0) }
        val rowScrollFirstItemOffset = remember { mutableStateOf(0) }
        val rowScrollStates = remember { mutableStateMapOf<Int, LazyListState>() }
        val rowScrollState = rememberLazyListState()
        LaunchedEffect(key1 = rowScrollState, block = {
            Logging.i("LaunchedEffect: ")
            snapshotFlow { rowScrollState.firstVisibleItemScrollOffset }.collect {
                Logging.i("offset[]: $it")
                rowScrollFirstItemIndex.value = rowScrollState.firstVisibleItemIndex
                rowScrollFirstItemOffset.value = rowScrollState.firstVisibleItemScrollOffset
                rowScrollStates.values.forEach { state ->
                    state.scrollToItem(rowScrollFirstItemIndex.value, rowScrollFirstItemOffset.value)
                }
            }
        })

        LazyColumn(
            content = {
                items(30) { row ->
                    val state = if (row == 0) {
                        rowScrollState
                    } else {
                        rowScrollStates[row] ?: rememberLazyListState(rowScrollFirstItemIndex.value, rowScrollFirstItemOffset.value)
                    }
                    rowScrollStates[row] = state
                    LazyRow(
                        state = rowScrollState,
                        content = {
                            items(30) { column ->
                                ListItem(modifier = Modifier.size(100.dp), data = "${column + 1}${column + 1}")
                            }
                        })
                }
            })
    }
}

