package com.example.composedemo.widget.scroll

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

private val colors = listOf(Color.Cyan, Color.Blue, Color.Green, Color.DarkGray, Color.Magenta)

@Composable
fun CustomGridViewDemo() {
    CustomGridView(
        factory = CustomGridViewFactoryImpl,
        item = { modifier, row, column, data ->
            ListItem(
                modifier = modifier.background(color = colors[row % colors.size]),
                data = data.toString()
            )
        }
    )
}

@Preview
@Composable
fun CustomGridViewDemoPreview() {
    MultipleScrollDemo()
}

