package com.example.composedemo.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun PreviewBox() {
    Row(modifier = Modifier.fillMaxWidth()) {
        // The child with no weight will have the specified size.
        Box(
            Modifier
                .size(40.dp, 80.dp)
                .weight(1f)
                .background(Color.Magenta)
        )
        // Has weight, the child will occupy half of the remaining width.
        Box(
            Modifier
                .height(40.dp)
                .weight(1f)
                .background(Color.Yellow)
        )
        // Has weight and does not fill, the child will occupy at most half of the remaining width.
        // Therefore it will occupy 80.dp (its preferred width) if the assigned width is larger.
        Box(
            Modifier
                .size(80.dp, 40.dp)
                .weight(1f)
                .background(Color.Green)
        )
    }
}