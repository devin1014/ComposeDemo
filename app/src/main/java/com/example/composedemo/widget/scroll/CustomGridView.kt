package com.example.composedemo.widget.scroll

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composedemo.Logging


@Composable
fun CustomGridView(
    factory: CustomGridViewFactory,
    item: @Composable (modifier: Modifier, row: Int, column: Int, data: Any) -> Unit
) {
    factory.checkColumnRowSize()
    Box(modifier = Modifier.fillMaxSize()) {
        val offset = remember { mutableStateOf(0) }
        val rowScrollState = rememberScrollState(offset.value)
        LaunchedEffect(key1 = rowScrollState, block = {
            snapshotFlow { rowScrollState.value }.collect {
                Logging.i("offset: $it")
                offset.value = it
            }
        })
        val firstColumnRowIndex = 0
        LazyColumn(
            content = {
                items(factory.getRowSize()) { row ->
                    Row(
                        modifier = Modifier
                            .height(factory.getRowHeight().dp)
                            .horizontalScroll(rowScrollState, enabled = factory.isRowScrollable(row))
                    ) {
                        (firstColumnRowIndex until factory.getColumnSize()).forEach { column ->
                            item(
                                modifier = Modifier
                                    .width(factory.getColumnWidth(column).dp)
                                    .height(factory.getRowHeight().dp)
                                    .clickable(enabled = factory.isItemClickable(row, column), onClick = {}),
                                row = row,
                                column = column,
                                data = factory.getItem(row, column)
                            )
                        }
                    }
                }
            })
    }
}

interface CustomGridViewFactory {
    fun getColumnWidth(index: Int): Int
    fun getColumnSize(): Int
    fun getRowHeight(): Int
    fun getRowSize(): Int
    fun isRowScrollable(index: Int): Boolean = true
    fun getItem(rowIndex: Int, columnIndex: Int): Any
    fun isItemClickable(rowIndex: Int, columnIndex: Int): Boolean = false

    fun checkColumnRowSize() {
        if (getColumnSize() <= 0) throw IllegalArgumentException("column size must larger than 0!")
        if (getRowSize() <= 0) throw IllegalArgumentException("row size must larger than 0!")
    }
}
