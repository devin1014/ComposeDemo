package com.example.composedemo.widget.scroll.stick_header

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composedemo.resources.getItemColor

@Composable
fun LazyRowStickHeaderDemo() {
    LazyRowStickHeader(
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
            .width(50.dp)
            .height(200.dp)
            .background(color = getItemColor(index)),
        contentAlignment = Alignment.Center
    ) {
        Text(text = index.toString())
    }
}