package com.example.composedemo.widget.collapsing_layout

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt

@Composable
fun CollapsingToolbar(
    toolbarHeight: Dp,
    collapsingToolbarHeight: Dp,
    offset: Float,
    modifier: Modifier = Modifier,
    title: (@Composable () -> Unit)? = null,
    navigationIcon: (@Composable () -> Unit)? = null,
    background: (@Composable () -> Unit)? = null,
) {
    val maxOffsetHeightPx = with(LocalDensity.current) {
        collapsingToolbarHeight.roundToPx().toFloat() - toolbarHeight.roundToPx().toFloat()
    }
    val titleHorizontalOffset = with(LocalDensity.current) {
        toolbarHeight.roundToPx().toFloat()
    }

    Box(modifier = Modifier
        .height(collapsingToolbarHeight)
        .offset { IntOffset(x = 0, y = offset.roundToInt()) }
        .fillMaxWidth()
    ) {
        background?.invoke()
        // navigation icon
        Row(
            modifier = modifier
                .offset { IntOffset(x = 0, y = -offset.roundToInt()) } // fix navigation icon position.
                .height(toolbarHeight)
                .fillMaxWidth(),
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
                .offset { IntOffset(x = -((offset / maxOffsetHeightPx) * titleHorizontalOffset).roundToInt(), y = 0) },
            contentAlignment = Alignment.CenterStart
        ) {
            title?.invoke()
        }
    }
}
