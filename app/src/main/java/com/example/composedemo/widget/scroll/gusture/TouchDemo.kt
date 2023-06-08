package com.example.composedemo.widget.scroll

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation.Vertical
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.lang.Float.min
import kotlin.math.max
import kotlin.math.roundToInt

@Composable
fun TouchDemo() {
//    ScrollDelta()
    PointInput()
}

@Composable
private fun ScrollDelta() {
    val offset = remember { mutableStateOf(0f) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .scrollable(
                orientation = Vertical,
                state = rememberScrollableState(consumeScrollDelta = { delta ->
                    offset.value = offset.value + delta
                    delta
                })
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = offset.value.toString(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun PointInput() {
    val boxSize = remember { mutableStateOf(IntSize(0, 0)) }
    val maxOffsetX = remember { mutableStateOf(0f) }
    val maxOffSetY = remember { mutableStateOf(0f) }
    val targetSize = with(LocalDensity) { 150 * this.current.density }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xffe2e2e2))
            .onSizeChanged {
                boxSize.value = it
                maxOffsetX.value = boxSize.value.width - targetSize
                maxOffSetY.value = boxSize.value.height - targetSize
            }
    ) {
        val offsetX = remember { mutableStateOf(0f) }
        val offsetY = remember { mutableStateOf(0f) }
        Box(
            modifier = Modifier
                .offset(
                    x = Dp(with(LocalDensity) { offsetX.value.div(this.current.density) }),
                    y = Dp(with(LocalDensity) { offsetY.value.div(this.current.density) }),
                )
                .background(color = Color.Blue)
                .border(color = Color.Red, width = 2.dp)
                .size(150.dp)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        offsetX.value = max(min(offsetX.value + dragAmount.x, maxOffsetX.value), 0f)
                        offsetY.value = max(min(offsetY.value + dragAmount.y, maxOffSetY.value), 0f)
                    }
                }
        )
        // des
        Column {
            Text(
                text = "Size: ${boxSize.value}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
            Box(modifier = Modifier.height(12.dp))
            Text(
                text = "X: ${offsetX.value.roundToInt()}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
            Text(
                text = "Y: ${offsetY.value.roundToInt()}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }
    }
}
