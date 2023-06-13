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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composedemo.resources.getItemColor

@Composable
fun LazyColumnStickHeaderDemo() {
    LazyColumnStickHeader(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Red),
        contentPadding = PaddingValues(bottom = 80.dp),
        content = {
            items(30) {
                Item(index = it)
            }
        },
        stickHeader = {
            Item(index = it)
        },
        isStick = {
            it % 5 == 0
        },
    )
}

@Composable
private fun Item(index: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(color = getItemColor(index)),
        contentAlignment = Alignment.Center
    ) {
        Text(text = index.toString())
    }
}

@Preview
@Composable
fun LazyColumnStickHeaderDemoPreview() {
    LazyColumnStickHeaderDemo()
}
