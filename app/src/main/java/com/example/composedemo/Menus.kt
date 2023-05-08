package com.example.composedemo

import androidx.compose.runtime.Composable
import com.example.composedemo.Menu.CanvasSample
import com.example.composedemo.Menu.CollapsingHeader
import com.example.composedemo.Menu.CommandSample
import com.example.composedemo.Menu.FocusSample
import com.example.composedemo.Menu.HorizontalPager
import com.example.composedemo.Menu.ImageFilterSample
import com.example.composedemo.Menu.ModifierDrawSample
import com.example.composedemo.Menu.ModifierSample
import com.example.composedemo.Menu.Nav1
import com.example.composedemo.Menu.Refresh
import com.example.composedemo.Menu.RowPager
import com.example.composedemo.widget.RowPagerDemo
import com.example.composedemo.widget.collapsing_layout.CollapsingHeaderDemo
import com.example.composedemo.widget.command.CommandDemo
import com.example.composedemo.widget.focus.FocusDemo
import com.example.composedemo.widget.modifier.CanvasDrawDemo
import com.example.composedemo.widget.modifier.ImageFilterDemo
import com.example.composedemo.widget.modifier.ModifierDemo
import com.example.composedemo.widget.modifier.ModifierDrawDemo
import com.example.composedemo.widget.pager.HorizontalPagerDemo
import com.example.composedemo.widget.refresh.RefreshDemo


sealed class Menu(val name: String, val router: String, val content: (@Composable () -> Unit)) {
    object Nav1 : Menu("Nav", "navPage1", {})
    object Nav2 : Menu("Nav2", "navPage2", {})
    object HorizontalPager : Menu("HorizontalPager", "horizontalPager", { HorizontalPagerDemo() })
    object RowPager : Menu("RowPager", "rowPager", { RowPagerDemo() })
    object CollapsingHeader : Menu("CollapsingHeader", "collapsingHeader", { CollapsingHeaderDemo() })
    object Refresh : Menu("Refresh", "refresh", { RefreshDemo() })
    object ModifierSample : Menu("Modifier", "modifier", { ModifierDemo() })
    object CanvasSample : Menu("Canvas", "canvas", { CanvasDrawDemo() })
    object ModifierDrawSample : Menu("ModifierDraw", "modifierDraw", { ModifierDrawDemo() })
    object ImageFilterSample : Menu("ImageFilter", "imageFilter", { ImageFilterDemo() })
    object FocusSample : Menu("FocusSample", "focusSample", { FocusDemo() })
    object CommandSample : Menu("CommandSample", "commandSample", { CommandDemo() })
}

val menuList = listOf(
    Nav1, HorizontalPager, RowPager, CollapsingHeader,
    Refresh, ModifierSample, CanvasSample, ModifierDrawSample,
    ImageFilterSample, FocusSample, CommandSample
)
