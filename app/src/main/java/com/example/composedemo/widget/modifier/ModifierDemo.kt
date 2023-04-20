package com.example.composedemo.widget.modifier

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush.Companion.horizontalGradient
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Brush.Companion.radialGradient
import androidx.compose.ui.graphics.Brush.Companion.sweepGradient
import androidx.compose.ui.graphics.Brush.Companion.verticalGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val brushColors = listOf(Color.Red, Color.Green, Color.Blue)

@Composable
fun ModifierDemo() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        ModifierPadding()
        CommonDivider()
        ModifierBackground()
        CommonDivider()
        ModifierBorder()
        CommonDivider()
        ModifierClip()
    }
}

@Composable
fun ModifierPadding() {
    Row(modifier = Modifier.fillMaxWidth()) {
        val bgModifier = Modifier.background(color = Color.Black)
        Box(
            modifier = bgModifier
                .size(60.dp)
        )
        CommonSpacer()
        Box(
            modifier = bgModifier
                .size(60.dp)
                .padding(10.dp)
                .background(color = Color.Red)
        )
        CommonSpacer()
        Box(
            modifier = bgModifier
                .padding(10.dp)
                .size(60.dp)
                .background(color = Color.Red)
        )
    }
}

@Composable
fun ModifierBackground() {
    Row(modifier = Modifier.fillMaxWidth()) {
        val bgModifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .weight(1f)
        Box(modifier = bgModifier.background(color = Color.Red, shape = RectangleShape))
        CommonSpacer()
        Box(modifier = bgModifier.background(color = Color.Red, shape = RoundedCornerShape(15)))
        CommonSpacer()
        Box(modifier = bgModifier.background(color = Color.Red, shape = CircleShape))
        CommonSpacer()

        Box(modifier = bgModifier.background(brush = linearGradient(brushColors), shape = RectangleShape))
        CommonSpacer()
        Box(modifier = bgModifier.background(brush = horizontalGradient(brushColors), shape = RoundedCornerShape(15)))
        CommonSpacer()
        Box(modifier = bgModifier.background(brush = verticalGradient(brushColors), shape = CircleShape))
        CommonSpacer()
    }
}

@Composable
fun ModifierBorder() {
    Row(modifier = Modifier.fillMaxWidth()) {
        val bgModifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .weight(1f)
        Box(modifier = bgModifier.border(width = 2.dp, color = Color.Red, shape = RectangleShape))
        CommonSpacer()
        Box(modifier = bgModifier.border(width = 2.dp, color = Color.Red, shape = RoundedCornerShape(15)))
        CommonSpacer()
        Box(modifier = bgModifier.border(width = 2.dp, color = Color.Red, shape = CircleShape))
        CommonSpacer()
        Box(modifier = bgModifier.border(width = 2.dp, brush = linearGradient(brushColors), shape = RectangleShape))
        CommonSpacer()
        Box(modifier = bgModifier.border(width = 2.dp, brush = radialGradient(brushColors), shape = RoundedCornerShape(15)))
        CommonSpacer()
        Box(modifier = bgModifier.border(width = 2.dp, brush = sweepGradient(brushColors), shape = CircleShape))
        CommonSpacer()
    }
}

@Composable
fun ModifierClip() {
    Row(modifier = Modifier.fillMaxWidth()) {
        val bgModifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .weight(1f)
            .background(color = Color.Red)
        Box(
            modifier = bgModifier
                .clip(CircleShape)
                .background(color = Color.White)
        )
        CommonSpacer()
        Box(
            modifier = bgModifier
                .clip(RoundedCornerShape(15))
                .background(color = Color.White)
        )
        CommonSpacer()
        Box(modifier = bgModifier)
        CommonSpacer()
        Box(modifier = bgModifier)
        CommonSpacer()
        Box(modifier = bgModifier)
        CommonSpacer()
        Box(modifier = bgModifier)
        CommonSpacer()
    }
}

@Composable
fun CommonDivider() {
    Divider(thickness = 12.dp, color = Color.LightGray)
}

@Composable
fun CommonSpacer(width: Dp = 4.dp) {
    Spacer(
        modifier = Modifier
            .width(width)
            .background(color = Color.Gray)
    )
}

@Preview(showBackground = true)
@Composable
fun ModifierDemoPreview() {
    ModifierDemo()
}