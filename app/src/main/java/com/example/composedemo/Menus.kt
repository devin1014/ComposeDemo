package com.example.composedemo

import androidx.compose.runtime.Composable
import com.example.composedemo.Menu.CollapsingHeader
import com.example.composedemo.Menu.HorizontalPager
import com.example.composedemo.Menu.Nav1
import com.example.composedemo.Menu.Refresh
import com.example.composedemo.Menu.RowPager
import com.example.composedemo.widget.RowPagerDemo
import com.example.composedemo.widget.collapsing_layout.CollapsingHeaderDemo
import com.example.composedemo.widget.pager.HorizontalPagerDemo
import com.example.composedemo.widget.refresh.RefreshDemo


sealed class Menu(val name: String, val router: String, val content: (@Composable () -> Unit)) {

    object Nav1 : Menu("Nav", "navPage1", {})
    object Nav2 : Menu("Nav2", "navPage2", {})
    object HorizontalPager : Menu("HorizontalPager", "horizontalPager", { HorizontalPagerDemo() })
    object RowPager : Menu("RowPager", "rowPager", { RowPagerDemo() })
    object CollapsingHeader : Menu("CollapsingHeader", "collapsingHeader", { CollapsingHeaderDemo() })

    object Refresh : Menu("Refresh", "refresh", { RefreshDemo() })
}

val menuList = listOf(Nav1, HorizontalPager, RowPager, CollapsingHeader, Refresh)
