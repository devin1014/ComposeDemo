package com.example.composedemo.widget.modifier

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CanvasDrawDemo() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        CanvasRect()
        CommonDivider()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .drawWithCache {
                    val path = Path()
                    path.moveTo(0f, 0f)
                    path.lineTo(size.width / 2f, size.height / 2f)
                    path.lineTo(size.width, 0f)
                    path.close()
                    onDrawBehind {
                        drawPath(path, Color.Magenta, style = Stroke(width = 10f))
                    }
                }
        )
    }
}

@Composable
fun CanvasRect() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        drawRect(color = Color.Magenta, size = size / 2F)
        val radius = size.width / 8F
        drawCircle(color = Color.Red, radius = radius, center = Offset(x = radius, y = radius))
        drawCircle(color = Color.Blue, radius = radius, center = Offset.Zero)
        drawLine(color = Color.Black, start = Offset(size.width / 2, 0f), end = Offset(size.width, size.height / 2), strokeWidth = 12f)
//        drawArc(color = Color.Black, startAngle = 0f, sweepAngle = 90f, useCenter = false)
        scale(scaleX = 2f, scaleY = 2f) {
            drawCircle(color = Color.Cyan, radius = 80f)
        }
        drawCircle(color = Color.Magenta, radius = 80f)
    }
}

@Preview(showBackground = true)
@Composable
fun CanvasDrawDemoPreview() {
    CanvasDrawDemo()
}