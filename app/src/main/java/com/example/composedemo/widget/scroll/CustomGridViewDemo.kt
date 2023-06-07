package com.example.composedemo.widget.scroll

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.composedemo.resources.getItemColor

@Composable
fun CustomGridViewDemo() {
    CustomGridView(
        factory = CustomGridViewFactoryImpl,
        item = { modifier, row, column, data ->
            ListItem(
                modifier = modifier.background(color = getItemColor(row)),
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

