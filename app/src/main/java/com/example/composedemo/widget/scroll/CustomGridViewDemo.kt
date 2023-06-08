package com.example.composedemo.widget.scroll

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.composedemo.resources.getItemColor

@Composable
fun CustomGridViewDemo() {
    CustomGridView(
        factory = CustomGridViewFactoryImpl,
        item = { modifier, row, column, data ->
            ListItem(
                modifier = modifier.background(color = getItemColor(row)),
                data = data.toString()
            )
        }
    )
}

@Preview
@Composable
fun CustomGridViewDemoPreview() {
    MultipleScrollDemo()
}

object CustomGridViewFactoryImpl : CustomGridViewFactory {
    private val columns = (1..30).map { it.toString() }
    private val rows = (1..30).map { it.toString() }
    private val list: List<List<String>> = mutableListOf<List<String>>().apply {
        rows.forEach { _ ->
            this.add(columns)
        }
    }
    override fun getColumnWidth(index: Int): Int = 100
    override fun getColumnSize(): Int = list.first().size
    override fun getRowHeight(): Int = 100
    override fun getRowSize(): Int = list.size
    override fun getItem(rowIndex: Int, columnIndex: Int): Any {
        return list[rowIndex][columnIndex]
    }
}
