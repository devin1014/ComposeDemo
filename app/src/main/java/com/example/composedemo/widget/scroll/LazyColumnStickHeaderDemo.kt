package com.example.composedemo.widget.scroll

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.composedemo.Logging
import com.example.composedemo.resources.getItemColor
import com.example.composedemo.widget.toPix
import kotlin.math.max

@Composable
fun LazyColumnStickHeaderDemo() {
    val itemHeight = 100.dp.toPix()
    Box(modifier = Modifier.fillMaxSize()) {
        val scrollOffset = remember { mutableStateOf(0) }
        val scrollState = rememberLazyListState()
        val stickHeaderOffset = remember { mutableStateOf(0) }
        val stickHerderPosition = remember { mutableStateOf(0) }
        LaunchedEffect(key1 = scrollState, block = {
            snapshotFlow { (scrollState.firstVisibleItemIndex * itemHeight) + scrollState.firstVisibleItemScrollOffset }.collect {
                Logging.d("scroll: $it, firstItem: ${scrollState.firstVisibleItemIndex}, itemOffset: ${scrollState.firstVisibleItemScrollOffset}")
                val scrollDown = it > scrollOffset.value
                scrollOffset.value = it
                //TODO: 向上滚动，stick header下拉出现有bug
                if (isStick(scrollState.firstVisibleItemIndex)) {
                    if (scrollDown) {
                        stickHerderPosition.value = scrollState.firstVisibleItemIndex
                    } else {
                        stickHerderPosition.value = findPreviousStick(scrollState.firstVisibleItemIndex)
                    }
                    stickHeaderOffset.value = 0
                } else if (isStick(scrollState.firstVisibleItemIndex + 1)) {
                    stickHeaderOffset.value = -scrollState.firstVisibleItemScrollOffset
                } else {
                    stickHeaderOffset.value = 0
                }
            }
        })
        LazyColumn(
            state = scrollState,
            content = {
                items(30) {
                    Item(index = it)
                }
            })
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .border(width = 2.dp, color = Color.Black)
                .offset(
                    x = 0.dp,
                    y = with(LocalDensity) { Dp(stickHeaderOffset.value / this.current.density) }
                ),
            contentAlignment = Alignment.Center
        ) {
            Item(index = stickHerderPosition.value)
        }
    }
}

private fun findPreviousStick(index: Int): Int {
    var position = index - 1
    while (position > 0 && !isStick(position)) {
        position--
    }
    return max(position, 0)
}

private fun isStick(index: Int): Boolean {
    if (index < 0) return false
    if (index >= 30) return false
    return index % 10 == 0
}

@Composable
private fun Item(index: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(color = if (isStick(index)) Color.White else getItemColor(index)),
        contentAlignment = Alignment.Center
    ) {
        Text(text = index.toString())
    }
}