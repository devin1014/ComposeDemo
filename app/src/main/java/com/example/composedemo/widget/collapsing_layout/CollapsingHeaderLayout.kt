package com.example.composedemo.widget.collapsing_layout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt


@Composable
fun CollapsingHeaderLayout(
    title: (@Composable () -> Unit)? = null,
    navigationIcon: (@Composable () -> Unit)? = null,
    header: (@Composable () -> Unit)? = null,
    contentSticker: (@Composable () -> Unit)? = null,
    lazyColumnContent: (LazyListScope.() -> Unit)? = null,
    scrollableContent: (@Composable () -> Unit)? = null,
    toolbarHeight: Dp = 56.dp,
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        val collapsingToolBarHeight = remember { mutableStateOf(0) }
        val maxUpPx = remember { mutableStateOf(0f) }
        val collapsingToolBarScrollState = remember { mutableStateOf(0f) }
        Box(
            Modifier
                .fillMaxSize()
                .nestedScroll(remember {
                    object : NestedScrollConnection {
                        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                            val delta = available.y
                            val newOffset = collapsingToolBarScrollState.value + delta
                            collapsingToolBarScrollState.value = newOffset.coerceIn(-maxUpPx.value, 0f)
                            return Offset.Zero
                        }
                    }
                })
        ) {
            if (contentSticker != null) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val contentStickerHeight = remember { mutableStateOf(0) }
                    val contentStickerHeightDp = contentStickerHeight.value.toDp()
                    val collapsingToolBarHeightDp = collapsingToolBarHeight.value.toDp()
                    if (lazyColumnContent != null) {
                        LazyColumn(
                            contentPadding = PaddingValues(top = collapsingToolBarHeightDp + contentStickerHeightDp)
                        ) {
                            lazyColumnContent(this)
                        }
                    }
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .offset { IntOffset(x = 0, y = collapsingToolBarScrollState.value.roundToInt()) }) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(collapsingToolBarHeightDp)
                        )
                        Box(
                            modifier = Modifier.onSizeChanged { contentStickerHeight.value = it.height }
                        ) {
                            contentSticker()
                        }
                    }
                    if (lazyColumnContent == null && scrollableContent != null) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(PaddingValues(top = collapsingToolBarHeightDp + contentStickerHeightDp + collapsingToolBarScrollState.value.toDp()))
                                .nestedScroll(remember {
                                    object : NestedScrollConnection {
                                        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                                            if (collapsingToolBarScrollState.value > -maxUpPx.value && collapsingToolBarScrollState.value < 0) {
                                                return available
                                            }
                                            return Offset.Zero
                                        }
                                    }
                                })
                        ) {
                            scrollableContent.invoke()
                        }
                    }
                }
            }
            // collapsing tool bar
            CollapsingToolbar(
                toolbarHeight = toolbarHeight,
                offset = collapsingToolBarScrollState.value,
                navigationIcon = navigationIcon,
                title = title,
                background = header
            ) { collapsingToolbarHeight, maxScrollDistance ->
                collapsingToolBarHeight.value = collapsingToolbarHeight
                maxUpPx.value = maxScrollDistance.toFloat()
            }
        }
    }
}

@Composable
private fun Float.toDp() = with(LocalDensity) { Dp(this@toDp / this.current.density) }

@Composable
private fun Int.toDp() = with(LocalDensity) { Dp(this@toDp / this.current.density) }
