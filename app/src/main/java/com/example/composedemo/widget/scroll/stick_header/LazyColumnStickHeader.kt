package com.example.composedemo.widget.scroll.stick_header

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
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
fun LazyColumnStickHeader(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    content: LazyListScope.() -> Unit,
    stickHeader: @Composable (index: Int) -> Unit,
    isStick: (index: Int) -> Boolean,
    onScrollChanged: ((firstVisibleItemIndex: Int, firstVisibleItemScrollOffset: Int) -> Unit)? = null,
    userScrollEnabled: Boolean = true,
) {
    Box(modifier = modifier) {
        val scrollOffset = remember { mutableStateOf(0) }
        val stickHeaderOffset = remember { mutableStateOf(0) }
        val stickHerderPosition = remember { mutableStateOf(0) }
        LaunchedEffect(key1 = state, block = {
            snapshotFlow { state.firstVisibleItemScrollOffset }.collect {
                Logging.d("firstItem: ${state.firstVisibleItemIndex}, itemOffset: ${state.firstVisibleItemScrollOffset}")
                val scrollDown = it > scrollOffset.value
                scrollOffset.value = it
                if (scrollDown) {
                    if (isStick(state.firstVisibleItemIndex)) {
                        stickHerderPosition.value = state.firstVisibleItemIndex
                        stickHeaderOffset.value = 0
                    } else if (isStick(state.firstVisibleItemIndex + 1)) {
                        stickHeaderOffset.value = -state.firstVisibleItemScrollOffset
                    } else {
                        stickHeaderOffset.value = 0
                    }
                } else {
                    if (isStick(state.firstVisibleItemIndex)) {
                        stickHeaderOffset.value = 0
                    } else if (isStick(state.firstVisibleItemIndex + 1)) {
                        stickHeaderOffset.value = -state.firstVisibleItemScrollOffset
                        stickHerderPosition.value = findPreviousStick(state.firstVisibleItemIndex, isStick = isStick)
                    } else {
                        stickHeaderOffset.value = 0
                    }
                }
                onScrollChanged?.invoke(state.firstVisibleItemIndex, state.firstVisibleItemScrollOffset)
            }
        })
        LazyColumn(
            state = state,
            content = { content(this) },
            userScrollEnabled = userScrollEnabled
        )
        Box(
            modifier = Modifier.offset(
                x = 0.dp,
                y = with(LocalDensity) { Dp(stickHeaderOffset.value / this.current.density) }
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
