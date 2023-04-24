package com.example.composedemo.widget.modifier

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composedemo.R.drawable

@Composable
fun ImageFilterDemo() {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.background(color = Color.Black)) {
            Image(
                painter = painterResource(id = drawable.league_pass_logo),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9)
                    .weight(1f),
                contentScale = ContentScale.Inside,
                colorFilter = ColorFilter.tint(Color.Red)
            )
            Image(
                painter = painterResource(id = drawable.league_pass_logo),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9)
                    .weight(1f),
                contentScale = ContentScale.Inside,
                colorFilter = ColorFilter.tint(Color.Red, BlendMode.SrcOut)
            )
            Image(
                painter = painterResource(id = drawable.league_pass_logo),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9)
                    .weight(1f),
                contentScale = ContentScale.Crop,
            )
            Image(
                painter = painterResource(id = drawable.league_pass_logo),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9)
                    .weight(1f),
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(Color.Green, blendMode = BlendMode.Darken)
            )
        }
        Divider()
        Row {
            Image(
                painter = painterResource(id = drawable.league_pass_logo2),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9)
                    .weight(1f),
                contentScale = ContentScale.Inside,
                colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })
            )
            val contrast = 2f // 0f..10f (1 should be default)
            val brightness = -180f // -255f..255f (0 should be default)
            val colorMatrix = floatArrayOf(
                contrast, 0f, 0f, 0f, brightness,
                0f, contrast, 0f, 0f, brightness,
                0f, 0f, contrast, 0f, brightness,
                0f, 0f, 0f, 1f, 0f
            )
            Image(
                painter = painterResource(id = drawable.league_pass_logo2),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9)
                    .weight(1f),
                contentScale = ContentScale.Inside,
                colorFilter = ColorFilter.colorMatrix(ColorMatrix(colorMatrix))
            )
            val colorMatrix2 = floatArrayOf(
                -1f, 0f, 0f, 0f, 255f,
                0f, -1f, 0f, 0f, 255f,
                0f, 0f, -1f, 0f, 255f,
                0f, 0f, 0f, 1f, 0f
            )
            Image(
                painter = painterResource(id = drawable.league_pass_logo2),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9)
                    .weight(1f),
                contentScale = ContentScale.Inside,
                colorFilter = ColorFilter.colorMatrix(ColorMatrix(colorMatrix2))
            )
            Image(
                painter = painterResource(id = drawable.league_pass_logo2),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9)
                    .weight(1f)
                    .blur(
                        radiusX = 10.dp,
                        radiusY = 10.dp,
                        edgeTreatment = BlurredEdgeTreatment.Unbounded
                    ),
                contentScale = ContentScale.Inside,
            )
        }
    }
}

@Preview
@Composable
fun ImageFilterDemoPreview() {
    ImageFilterDemo()
}