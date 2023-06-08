package com.example.composedemo.widget.scroll

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composedemo.resources.getItemColor

@Composable
fun LazyColumnStickHeaderDemo() {
    LazyColumnStickHeader(
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