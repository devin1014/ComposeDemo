package com.example.composedemo.resources

import androidx.compose.ui.graphics.Color

val itemColors = listOf(Color.Cyan, Color.Blue, Color.Green, Color.DarkGray, Color.Magenta)

fun getItemColor(index: Int) = itemColors[index % itemColors.size]
