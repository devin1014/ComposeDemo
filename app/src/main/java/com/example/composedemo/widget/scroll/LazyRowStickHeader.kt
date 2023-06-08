package com.example.composedemo.widget.scroll

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.composedemo.Logging
import kotlin.math.max

@Composable
fun LazyRowStickHeader(
    scrollState: LazyListState = rememberLazyListState(),
    content: LazyListScope.() -> Unit,
    stickHeader: @Composable (index: Int) -> Unit,
    isStick: (index: Int) -> Boolean,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        val itemWidth = 99 // fake value
        val scrollOffset = remember { mutableStateOf(0) }
        val stickHeaderOffset = remember { mutableStateOf(0) }
        val stickHerderPosition = remember { mutableStateOf(0) }

        LaunchedEffect(key1 = scrollState, block = {
            snapshotFlow { (scrollState.firstVisibleItemIndex * itemWidth) + scrollState.firstVisibleItemScrollOffset }.collect {
                Logging.d("scroll: $it, position: ${scrollState.firstVisibleItemIndex}, offset: ${scrollState.firstVisibleItemScrollOffset}")
                val scrollRight = it > scrollOffset.value
                scrollOffset.value = it
                if (scrollRight) {
                    if (isStick(scrollState.firstVisibleItemIndex)) {
                        stickHerderPosition.value = scrollState.firstVisibleItemIndex
                        stickHeaderOffset.value = 0
                    } else if (isStick(scrollState.firstVisibleItemIndex + 1)) {
                        stickHeaderOffset.value = -scrollState.firstVisibleItemScrollOffset
                    } else {
                        stickHeaderOffset.value = 0
                    }
                } else {
                    if (isStick(scrollState.firstVisibleItemIndex)) {
                        stickHeaderOffset.value = 0
                    } else if (isStick(scrollState.firstVisibleItemIndex + 1)) {
                        stickHeaderOffset.value = -scrollState.firstVisibleItemScrollOffset
                        stickHerderPosition.value = findPreviousStick(scrollState.firstVisibleItemIndex, isStick = isStick)
                    } else {
                        stickHeaderOffset.value = 0
                    }
                }
            }
        })
        LazyRow(
            state = scrollState,
            content = { content(this) })
        Box(
            modifier = Modifier.offset(
                x = with(LocalDensity) { Dp(stickHeaderOffset.value / this.current.density) },
                y = 0.dp
            )
        ) {
            stickHeader(index = stickHerderPosition.value)
        }
    }
}

private fun findPreviousStick(index: Int, isStick: (index: Int) -> Boolean): Int {
    var position = index - 1
    while (position > 0 && !isStick(position)) {
        position--
    }
    return max(position, 0)
}
