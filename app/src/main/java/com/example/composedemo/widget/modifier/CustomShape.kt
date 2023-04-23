package com.example.composedemo.widget.modifier

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class SquashedOval : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            // We create an Oval that starts at ¼ of the width, and ends at ¾ of the width of the container.
            addOval(
                Rect(
                    left = size.width / 4f,
                    top = 0f,
                    right = size.width * 3 / 4f,
                    bottom = size.height
                )
            )
        }
        return Outline.Generic(path = path)
    }
}

class TriangleShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            moveTo(x = 0f, y = size.height / 5)
            lineTo(x = size.width, y = size.height / 5)
            lineTo(x = size.width / 2, y = size.height)
            close()
        }
        return Outline.Generic(path = path)
    }
}

class StarShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            moveTo(x = 0f, y = size.height / 3)
            lineTo(x = size.width, y = size.height / 3)
            lineTo(x = size.width / 5, y = size.height)
            lineTo(x = size.width / 2, y = 0f)
            lineTo(x = size.width / 5 * 4, y = size.height)
            close()
        }
        return Outline.Generic(path = path)
    }
}