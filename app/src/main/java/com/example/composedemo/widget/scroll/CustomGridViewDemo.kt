package com.example.composedemo.widget.scroll

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CustomGridViewDemo() {
    val factory = CustomGridViewFactoryImpl
    CustomGridView(
        factory = factory,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 80.dp),
        item = { modifier, row, column, data ->
            var itemData = data.toString()
            val m2: Modifier = if (column != 0 && !factory.isRowScrollable(row)) {
                itemData = "No Data"
                modifier.background(color = Color.Gray)
            } else if (row == 0 || factory.isStickHeader(row)) {
                modifier.background(color = Color.Cyan)
            } else if (column == 0) {
                modifier.background(color = Color.Yellow)
            } else {
                modifier
            }
            ListItem(
                modifier = m2,
                data = itemData
            )
        }
    )
}

@Preview
@Composable
fun CustomGridViewDemoPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(color = Color.Red)
    ) {
        CustomGridViewDemo()
    }
}

object CustomGridViewFactoryImpl : CustomGridViewFactory {
    private val columns = (1..20).map { it.toString() }
    private val rows = (1..20).map { it.toString() }
    private val list: List<List<String>> = mutableListOf<List<String>>().apply {
        rows.forEach { _ ->
            this.add(columns)
        }
    }

    override fun getColumnWidth(column: Int): Int = if (column == 0) 100 else 50
    override fun getColumnSize(): Int = list.first().size
    override fun getRowHeight(): Int = 50
    override fun getRowSize(): Int = list.size
    override fun getItem(row: Int, column: Int): Any = list[row][column]
    override fun isStickHeader(row: Int): Boolean = row % 5 == 0
    override fun isRowScrollable(row: Int): Boolean = row == 0 || row % 7 != 0
}
