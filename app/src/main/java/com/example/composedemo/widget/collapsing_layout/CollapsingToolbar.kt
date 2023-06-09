package com.example.composedemo.widget.collapsing_layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun CollapsingTitleBar(
    offset: Float,
    toolbarHeight: Dp = 56.dp,
    toolbarBackground: Color = Color.Transparent,
    collapsingTitle: (@Composable () -> Unit)? = null,
    navigationIcon: (@Composable () -> Unit)? = null,
    content: (@Composable () -> Unit)? = null,
    onSizeChanged: (collapsingTooBarHeight: Int, maxScrollDistance: Int) -> Unit
) {
    val collapsingToolbarHeight = remember { mutableStateOf(0) }
    val maxScrollableHeight = with(LocalDensity.current) { collapsingToolbarHeight.value - toolbarHeight.roundToPx() }
    val titleHorizontalOffset = with(LocalDensity.current) { toolbarHeight.roundToPx().toFloat() }
    if (collapsingToolbarHeight.value > 0 && maxScrollableHeight > 0) {
        onSizeChanged(collapsingToolbarHeight.value, maxScrollableHeight)
    }
    Box(modifier = Modifier
        .onSizeChanged {
            collapsingToolbarHeight.value = it.height
        }
        .offset { IntOffset(x = 0, y = offset.roundToInt()) }
        .fillMaxWidth()
    ) {
        content?.invoke()
        // navigation icon
        Row(
            modifier = Modifier
                .offset { IntOffset(x = 0, y = -offset.roundToInt()) } // fix navigation icon position.
                .height(toolbarHeight)
                .fillMaxWidth()
                .background(color = toolbarBackground),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(toolbarHeight),
                contentAlignment = Alignment.Center
            ) {
                navigationIcon?.invoke()
            }
        }
        // title
        Box(
            modifier = Modifier
                .height(toolbarHeight)
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .offset { IntOffset(x = -((offset / maxScrollableHeight) * titleHorizontalOffset).roundToInt(), y = 0) },
            contentAlignment = Alignment.CenterStart
        ) {
            collapsingTitle?.invoke()
        }
    }
}

@Composable
fun CollapsingToolbar(
    offset: Float,
    toolbar: @Composable () -> Unit,
    content: (@Composable () -> Unit)? = null,
    onSizeChanged: (collapsingTooBarHeight: Int, maxScrollDistance: Int) -> Unit
) {
    val collapsingToolbarHeight = remember { mutableStateOf(0) }
    val toolbarHeight = remember { mutableStateOf(0) }
    val maxScrollableHeight = collapsingToolbarHeight.value - toolbarHeight.value
    if (collapsingToolbarHeight.value > 0 && maxScrollableHeight > 0) {
        onSizeChanged(collapsingToolbarHeight.value, maxScrollableHeight)
    }
    Box(modifier = Modifier
        .onSizeChanged { collapsingToolbarHeight.value = it.height }
        .offset { IntOffset(x = 0, y = offset.roundToInt()) }
        .fillMaxWidth()
    ) {
        content?.invoke()
        // navigation icon
        Row(
            modifier = Modifier
                .onSizeChanged { toolbarHeight.value = it.height }
                .offset { IntOffset(x = 0, y = -offset.roundToInt()) } // fix navigation icon position.
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            toolbar()
        }
    }
}
