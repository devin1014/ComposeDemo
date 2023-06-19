package com.example.composedemo.widget.scroll

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composedemo.widget.scroll.stick_header.LazyColumnStickHeader
import kotlinx.coroutines.launch


@Composable
fun CustomGridView(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    factory: CustomGridViewFactory,
    item: @Composable (modifier: Modifier, row: Int, maxRow: Int, column: Int, maxColumn: Int, data: Any) -> Unit,
    onItemClick: ((row: Int, column: Int) -> Unit)? = null
) {
    Row(modifier = modifier) {
//        val offset = remember { mutableStateOf(0) }
        val rowScrollState = rememberScrollState()
//        LaunchedEffect(key1 = rowScrollState, block = {
//            snapshotFlow { rowScrollState.value }.collect {
//                offset.value = it
//            }
//        })
        val maxRow: Int = factory.getRowSize() - 1
        val maxColumn: Int = factory.getColumnSize() - 1
        val coroutineScope = rememberCoroutineScope()
        val leftListState = rememberLazyListState()
        //left
        LazyColumnStickHeader(
            state = leftListState,
            modifier = Modifier.fillMaxHeight(),
            contentPadding = contentPadding,
            content = {
                items(factory.getRowSize()) { row ->
                    val column = 0
                    item(
                        modifier = Modifier.itemSize(factory, row, column, onItemClick),
                        row = row,
                        maxRow = maxRow,
                        column = column,
                        maxColumn = maxColumn,
                        data = factory.getItem(row, column),
                    )
                }
            },
            stickHeader = { row ->
                val column = 0
                item(
                    modifier = Modifier.itemSize(factory, row, column, onItemClick),
                    row = row,
                    maxRow = maxRow,
                    column = column,
                    maxColumn = maxColumn,
                    data = factory.getItem(row, column),
                )
            },
            isStick = factory::isStickHeader,
            userScrollEnabled = false
        )
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(0.5.dp)
                .background(color = Color.LightGray)
        )
        //right
        val rightContentFirstColumn = 1
        LazyColumnStickHeader(
            modifier = Modifier.fillMaxHeight(),
            contentPadding = contentPadding,
            content = {
                items(factory.getRowSize()) { row ->
                    if (factory.isRowScrollable(row)) {
                        Row(
                            modifier = Modifier
                                .height(factory.getRowHeight().dp)
                                .horizontalScroll(state = rowScrollState) //TODO:
                        ) {
                            (rightContentFirstColumn until factory.getColumnSize()).forEach { column ->
                                item(
                                    modifier = Modifier.itemSize(factory, row, column, onItemClick),
                                    row = row,
                                    maxRow = maxRow,
                                    column = column,
                                    maxColumn = maxColumn,
                                    data = factory.getItem(row, column),
                                )
                            }
                        }
                    } else {
                        item(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(factory.getRowHeight().dp),
                            row = row,
                            maxRow = maxRow,
                            column = rightContentFirstColumn,
                            maxColumn = maxColumn,
                            data = factory.getItem(row, rightContentFirstColumn),
                        )
                    }
                }
            },
            stickHeader = { row ->
                Row(
                    modifier = Modifier
                        .height(factory.getRowHeight().dp)
                        .horizontalScroll(state = rowScrollState, enabled = factory.isRowScrollable(row))
                ) {
                    (rightContentFirstColumn until factory.getColumnSize()).forEach { column ->
                        item(
                            modifier = Modifier.itemSize(factory, row, column, onItemClick),
                            row = row,
                            maxRow = maxRow,
                            column = column,
                            maxColumn = maxColumn,
                            data = factory.getItem(row, column),
                        )
                    }
                }
            },
            isStick = factory::isStickHeader,
            onScrollChanged = { firstVisibleItemIndex: Int, firstVisibleItemScrollOffset: Int ->
                coroutineScope.launch {
                    leftListState.scrollToItem(firstVisibleItemIndex, firstVisibleItemScrollOffset)
                }
            }
        )
    }
}

private fun Modifier.itemSize(
    factory: CustomGridViewFactory,
    row: Int,
    column: Int,
    onItemClick: ((row: Int, column: Int) -> Unit)?
) = this.then(
    Modifier
        .width(factory.getColumnWidth(column).dp)
        .height(factory.getRowHeight().dp)
        .clickable(
            enabled = factory.isItemClickable(row, column),
            onClick = {
                onItemClick?.invoke(row, column)
            })
)

interface CustomGridViewFactory {
    fun getColumnWidth(column: Int): Int
    fun getColumnSize(): Int
    fun getRowHeight(): Int
    fun getRowSize(): Int
    fun isRowScrollable(row: Int): Boolean = true
    fun getItem(row: Int, column: Int): Any
    fun isStickHeader(row: Int): Boolean
    fun isItemClickable(row: Int, column: Int): Boolean = false
}

@Preview
@Composable
fun CustomGridViewPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Magenta)
    ) {
        val factory = SampleFactory()
        CustomGridView(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 40.dp),
            factory = factory,
            item = { modifier, row, column, maxRow, maxColumn, data ->
                val stickHeader = factory.isStickHeader(row)
                val lastRow = row == factory.getRowSize() - 1
                Box(
                    modifier = modifier.background(color = if (lastRow) Color.Red else Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = data.toString(), color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = if (stickHeader) FontWeight.Bold else null
                    )
                }
            })
    }
}

private class SampleFactory : CustomGridViewFactory {
    override fun getColumnWidth(column: Int): Int = if (column == 0) 120 else 120
    override fun getColumnSize(): Int = 6
    override fun getRowHeight(): Int = 60
    override fun getRowSize(): Int = 20
    override fun getItem(row: Int, column: Int): Any = "${row}_$column"
    override fun isStickHeader(row: Int): Boolean = row % 5 == 0
}
