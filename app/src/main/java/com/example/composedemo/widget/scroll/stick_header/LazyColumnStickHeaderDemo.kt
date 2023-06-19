package com.example.composedemo.widget.scroll.stick_header

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LazyColumnStickHeaderDemo() {
    LazyColumnStickHeader(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        contentPadding = PaddingValues(bottom = 50.dp),
        content = {
            items(30) {
                Item(index = it, color = if (it % 10 == 0) Color.Cyan else Color.Transparent)
            }
        },
        stickHeader = {
            Item(index = it, color = Color.Cyan)
        },
        isStick = {
            it % 10 == 0
        },
    )
}

@Composable
private fun Item(index: Int, color: Color = Color.Transparent) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(color = color),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = index.toString(),
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
fun LazyColumnStickHeaderDemoPreview() {
    LazyColumnStickHeaderDemo()
}
