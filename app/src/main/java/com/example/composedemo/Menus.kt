package com.example.composedemo

import androidx.compose.runtime.Composable
import com.example.composedemo.Menu.CanvasSample
import com.example.composedemo.Menu.CollapsingHeader
import com.example.composedemo.Menu.CommandSample
import com.example.composedemo.Menu.CustomGridViewSample
import com.example.composedemo.Menu.FocusSample
import com.example.composedemo.Menu.GestureScrollSample
import com.example.composedemo.Menu.HorizontalPager
import com.example.composedemo.Menu.ImageFilterSample
import com.example.composedemo.Menu.LazyColumnScrollSample
import com.example.composedemo.Menu.LazyColumnStickHeaderSample
import com.example.composedemo.Menu.LazyGridSample
import com.example.composedemo.Menu.LazyRowStickHeaderSample
import com.example.composedemo.Menu.ModifierDrawSample
import com.example.composedemo.Menu.ModifierSample
import com.example.composedemo.Menu.MotionLayout
import com.example.composedemo.Menu.MotionSample
import com.example.composedemo.Menu.MultipleScrollSample
import com.example.composedemo.Menu.NavSample
import com.example.composedemo.Menu.Refresh
import com.example.composedemo.Menu.RowPager
import com.example.composedemo.Menu.RowScrollableSample
import com.example.composedemo.Menu.TouchSample
import com.example.composedemo.motion.MotionActivity
import com.example.composedemo.motion.MotionDemo
import com.example.composedemo.nav.NavDemo
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
import com.example.composedemo.widget.scroll.CustomGridViewDemo
import com.example.composedemo.widget.scroll.LazyColumnScrollDemo
import com.example.composedemo.widget.scroll.LazyGridDemo
import com.example.composedemo.widget.scroll.MultipleScrollDemo
import com.example.composedemo.widget.scroll.RowScrollableDemo
import com.example.composedemo.widget.scroll.TouchDemo
import com.example.composedemo.widget.scroll.gusture.GestureScrollDemo
import com.example.composedemo.widget.scroll.stick_header.LazyColumnStickHeaderDemo
import com.example.composedemo.widget.scroll.stick_header.LazyRowStickHeaderDemo
import kotlin.reflect.KClass

sealed class Menu(val name: String, val router: String, val content: (@Composable () -> Unit)) {
    object NavSample : Menu("Nav", "navPage", { NavDemo() })
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
    object MultipleScrollSample : Menu("MultipleScrollSample", "multipleScrollSample", { MultipleScrollDemo() })
    object TouchSample : Menu("TouchSample", "touchSample", { TouchDemo() })
    object LazyColumnScrollSample : Menu("LazyColumnScrollSample", "lazyColumnScrollSample", { LazyColumnScrollDemo() })
    object RowScrollableSample : Menu("RowScrollableDemo", "RowScrollableDemo", { RowScrollableDemo() })
    object CustomGridViewSample : Menu("CustomGridViewDemo", "CustomGridViewDemo", { CustomGridViewDemo() })
    object LazyColumnStickHeaderSample : Menu("LazyColumnStickHeaderDemo", "LazyColumnStickHeaderDemo", { LazyColumnStickHeaderDemo() })
    object LazyRowStickHeaderSample : Menu("LazyRowStickHeaderDemo", "LazyRowStickHeaderDemo", { LazyRowStickHeaderDemo() })
    object LazyGridSample : Menu("LazyGridDemo", "LazyGridDemo", { LazyGridDemo() })
    object GestureScrollSample : Menu("GestureScrollDemo", "GestureScrollDemo", { GestureScrollDemo() })
    object MotionSample : Menu("MotionDemo", "MotionDemo", { MotionDemo() }), DefaultPage

    // Activity
    object MotionLayout : Menu("MotionLayout", "MotionLayout", { throw IllegalArgumentException("should not call this function!") }), ActivityPage {
        override val targetPage: KClass<*> = MotionActivity::class
    }
}

interface ActivityPage {
    val targetPage: KClass<*>
}

interface DefaultPage

interface AutoLink

val menuList = listOf(
    NavSample, HorizontalPager, RowPager, CollapsingHeader,
    Refresh, ModifierSample, CanvasSample, ModifierDrawSample,
    ImageFilterSample, FocusSample, CommandSample,
    MultipleScrollSample, TouchSample, LazyColumnScrollSample,
    RowScrollableSample, CustomGridViewSample, LazyColumnStickHeaderSample,
    LazyRowStickHeaderSample, LazyGridSample, GestureScrollSample,
    MotionLayout, MotionSample,
)
