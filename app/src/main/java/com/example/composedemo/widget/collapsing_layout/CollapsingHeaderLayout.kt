package com.example.composedemo.widget.collapsing_layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt


@Composable
fun CollapsingHeaderLayout(
    toolbarHeight: Dp = 56.dp,
    collapsingToolbarLayoutHeight: Dp,
    title: (@Composable () -> Unit)? = null,
    navigationIcon: (@Composable () -> Unit)? = null,
    background: (@Composable () -> Unit)? = null,
    content: @Composable (scrollOffset: Int, maxScrollRange: Int) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        val maxUpPx = with(LocalDensity.current) { collapsingToolbarLayoutHeight.roundToPx().toFloat() - toolbarHeight.roundToPx().toFloat() }
        val minUpPx = 0f
        val toolbarOffsetHeightPx = remember { mutableStateOf(0f) }
        val nestedScrollConnection = remember {
            object : NestedScrollConnection {
                override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                    val delta = available.y
                    val newOffset = toolbarOffsetHeightPx.value + delta
                    toolbarOffsetHeightPx.value = newOffset.coerceIn(-maxUpPx, minUpPx)
                    return Offset.Zero
                }
            }
        }
        Box(
            Modifier
                .fillMaxSize()
                .nestedScroll(nestedScrollConnection)
        ) {
            content(toolbarOffsetHeightPx.value.roundToInt(), maxUpPx.roundToInt())
            CollapsingToolbar(
                toolbarHeight = toolbarHeight,
                collapsingToolbarHeight = collapsingToolbarLayoutHeight,
                offset = toolbarOffsetHeightPx.value,
                navigationIcon = navigationIcon,
                title = title,
                background = background
            )
        }
    }
}