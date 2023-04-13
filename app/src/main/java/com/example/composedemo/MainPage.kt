package com.example.composedemo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells.Adaptive
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composedemo.Menu.*


sealed class Menu(val name: String, val router: String) {

    object Nav1 : Menu("Nav", "navPage1")
    object Nav2 : Menu("Nav2", "navPage2")
    object Pager : Menu("Pager", "horizontalPager")
    object RowPager : Menu("RowPager", "rowPager")
    object ScrollableAppBar : Menu("ScrollableAppBar", "scrollableAppBar")
}

private val bgColors = listOf(Color.Cyan, Color.Magenta, Color.LightGray, Color.Yellow, Color.Blue, Color.Green)

private val menuList = listOf(Nav1, Pager, RowPager, ScrollableAppBar)

@Composable
fun MainPage(onClick: (Int, String) -> Unit) {
    MaterialTheme {
        LazyVerticalGrid(
            columns = Adaptive(minSize = 128.dp),
            content = {
                itemsIndexed(menuList) { index, menu ->
                    Box(
                        modifier = Modifier
                            .height(128.dp)
                            .background(color = bgColors[index % bgColors.size])
                            .clickable { onClick.invoke(index, menu.router) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = menu.name,
                            color = Color.Black,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            })
    }
}