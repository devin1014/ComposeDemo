package com.example.composedemo.widget.scroll

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composedemo.Logging
import com.example.composedemo.widget.scroll.stick_header.LazyColumnStickHeader
import kotlinx.coroutines.launch


@Composable
fun CustomGridView(
    factory: CustomGridViewFactory,
    item: @Composable (modifier: Modifier, row: Int, column: Int, data: Any) -> Unit,
    onItemClick: ((row: Int, column: Int) -> Unit)? = null
) {
    Row(modifier = Modifier.fillMaxSize()) {
        val offset = remember { mutableStateOf(0) }
        val rowScrollState = rememberScrollState(offset.value)
        LaunchedEffect(key1 = rowScrollState, block = {
            snapshotFlow { rowScrollState.value }.collect {
                Logging.i("offset: $it")
                offset.value = it
            }
        })
        val coroutineScope = rememberCoroutineScope()
        val leftListState = rememberLazyListState()
        //left
        LazyColumnStickHeader(
            state = leftListState,
            content = {
                items(factory.getRowSize()) { row ->
                    val column = 0
                    item(
                        modifier = Modifier.itemSize(factory, row, column, onItemClick),
                        row = row,
                        column = column,
                        data = factory.getItem(row, column),
                    )
                }
            },
            stickHeader = { row ->
                val column = 0
                item(
                    modifier = Modifier.itemSize(factory, row, column, onItemClick),
                    row = row,
                    column = column,
                    data = factory.getItem(row, column),
                )
            },
            isStick = factory::isStickHeader,
            userScrollEnabled = false
        )
        //right
        val rightContentFirstColumn = 1
        LazyColumnStickHeader(
            content = {
                items(factory.getRowSize()) { row ->
                    if (factory.isRowScrollable(row)) {
                        Row(
                            modifier = Modifier
                                .height(factory.getRowHeight().dp)
                                .horizontalScroll(state = rowScrollState)
                        ) {
                            (rightContentFirstColumn until factory.getColumnSize()).forEach { column ->
                                item(
                                    modifier = Modifier.itemSize(factory, row, column, onItemClick),
                                    row = row,
                                    column = column,
                                    data = factory.getItem(row, column),
                                )
                            }
                        }
                    } else {
//                        Box(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(factory.getRowHeight().dp)
//                        ) {
                        item(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(factory.getRowHeight().dp),
                            row = row,
                            column = rightContentFirstColumn,
                            data = factory.getItem(row, rightContentFirstColumn),
                        )
//                        }
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
                            column = column,
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
